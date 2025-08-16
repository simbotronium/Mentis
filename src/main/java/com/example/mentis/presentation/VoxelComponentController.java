package com.example.mentis.presentation;

import com.example.mentis.business.data.Voxel;
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
        voxel.validationStatusProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Validation status changed");
        });
        voxel.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                root.setStroke(Color.WHITE);
            } else {
                root.setStroke(voxel.getValidationStatus().getColor());
            }
        });
    }

    @Override
    public VoxelComponent getRoot() {
        return root;
    }
}
