package com.github.jbharter.Models;

import com.google.api.services.sheets.v4.model.Sheet;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Model {

    protected static final Set<Model> instanceList = new HashSet<>();

    protected static final void addSelfToInstanceList(Model model) {
        instanceList.add(model);
    }

    protected static final void removeSelfFromInstanceList(Model model) {
        instanceList.remove(model);
    }

    public static Collection<Table> ofTables(List<Sheet> sheets){
        return sheets.stream().map(Table::new).collect(Collectors.toList());
    }

    public abstract void appendModel(Model m);

    public static Model squish(Collection<Model> models) {
        List<Model> l = new ArrayList<>(models);
        Model model = l.remove(0);
        l.forEach(model::appendModel);

        return model;
    }

}
