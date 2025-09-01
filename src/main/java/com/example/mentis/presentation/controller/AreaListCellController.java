package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.presentation.components.AreaListCell;

public class AreaListCellController implements Controller {

    private final AreaListCell root;

    public AreaListCellController(AreaListCell root, Area area) {
        this.root = root;

        root.getDelete().setOnAction(e -> Manager.getInstance().getCurrentProject().deleteArea(100L));
    }

    @Override
    public AreaListCell getRoot() {
        return root;
    }

}
