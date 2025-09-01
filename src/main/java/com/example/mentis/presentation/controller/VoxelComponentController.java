package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Voxel;
import com.example.mentis.presentation.components.VoxelComponent;
import javafx.scene.paint.Color;

public class VoxelComponentController implements Controller {
    private final Voxel voxel;
    private final VoxelComponent root;

    public VoxelComponentController(VoxelComponent component, Voxel voxel) {
        this.root = component;
        this.voxel = voxel;

        init();
    }

    private void init() {
        root.getContainer().setOnMouseEntered(e -> root.animateHover(root.getContainer(), 2.0, root.getFillColor().deriveColor(0, 1, 1, 0.8), 150));
        root.getContainer().setOnMouseExited(e -> {
            if (!voxel.isSelected()) root.animateHover(root.getContainer(), 1, root.getFillColor().deriveColor(0, 1, 1, 0.5), 200);
        });

        voxel.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                root.setStroke(Color.WHITE);
            } else {
                // TODO: setup for different grid positions
                root.setColors();
                root.animateHover(root.getContainer(), 1, root.getFillColor().deriveColor(0, 1, 1, 0.5), 200);
            }
        });

        voxel.validationStatusProperty().addListener((observable, oldValue, newValue) -> root.setColors());
        voxel.areaProperty().addListener((observable, oldValue, newValue) -> root.setColors());
    }


    @Override
    public VoxelComponent getRoot() {
        return root;
    }
}
