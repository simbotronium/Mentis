package com.example.mentis.business.data;

import javafx.scene.paint.Color;

public record Area(long id, Color color, String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Area)) return false;
        return this.id == ((Area) o).id;
    }

}
