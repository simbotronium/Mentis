package com.example.mentis.presentation;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.logic.Manager;

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
