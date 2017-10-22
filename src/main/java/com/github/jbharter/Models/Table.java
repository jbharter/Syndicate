package com.github.jbharter.Models;

import com.google.api.services.sheets.v4.model.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class Table extends Model {

    //private final Collection<GridData> data;
    //private final Collection<RowData> rowData;
    //private final SheetProperties properties;
    private final Sheet m_sheet;

    //private List<CellData> headers;
    private List<String> interpretedHeaders = new ArrayList<>();

    public final Supplier<String> title;
    public final Supplier<SheetProperties> properties;
    public final Supplier<List<RowData>> data;
    public final Supplier<List<CellData>> headers;
    public final Supplier<Sheet> sheet;
    public final IntSupplier id;
    public final Function<Integer,List<CellData>> getRow;
    public final CopyOnWriteArrayList<Map<String,String>> rows = new CopyOnWriteArrayList<>();

    public Table(Sheet s) {

        this.m_sheet = s;

        this.sheet      = () -> this.m_sheet;
        this.properties = () -> sheet.get().getProperties();
        this.data       = () -> sheet.get().getData().get(0).getRowData();
        this.title      = () -> properties.get().getTitle();
        this.id         = () -> properties.get().getSheetId();
        this.getRow     = row -> data.get().get(row).getValues();
        this.headers    = () -> getRow.apply(0);

        headers.get().forEach(cellData -> {
            String formattedValue = cellData.getFormattedValue();
            interpretedHeaders.add((formattedValue != null) ? formattedValue : "");
        });

        Queue<Map<String,String>> rowe = new ConcurrentLinkedQueue<>();
        rowe = data
            .get()
            .parallelStream()
            .map(row -> {
                List<CellData> rowVals = row.getValues();
                Map<String,String> rowMap = new LinkedHashMap<>();
                for (int i = 0; i < rowVals.size(); ++i) {
                    String candidVal = rowVals.get(i).getFormattedValue();
                    rowMap.put(interpretedHeaders.get(i),(candidVal != null) ? candidVal : "");
                }
                return rowMap;
            }).collect(ConcurrentLinkedQueue::new,
                       ConcurrentLinkedQueue::add,
                       AbstractCollection::addAll);

        this.rows.addAll(rowe);

        addSelfToInstanceList(this);
    }

    public void removeCol(int col) {

        if (interpretedHeaders.get(col) == null) return;
        String colToRemove = interpretedHeaders.remove(col);

        rows.parallelStream().forEach(row -> {
            row.remove(colToRemove);
        });
    }

    public void removeCol(String col) {

        if (!interpretedHeaders.contains(col)) return;

        interpretedHeaders.remove(col);
        rows.parallelStream().forEach(row -> {
            row.remove(col);
        });
    }

    public void appendModel(Model m) {
        if (m instanceof Table) {
            appendTable((Table)m);
        }
    }

    public void appendTable(Table t) {
        // keep cols from incoming where we have a match
        List<String> otherHeaders = new ArrayList<>(t.interpretedHeaders);
        otherHeaders.removeAll(interpretedHeaders);

        otherHeaders.forEach(t::removeCol);

        // Collect underlying rows
        rows.addAll(t.rows);
        removeSelfFromInstanceList(t);

    }
}
