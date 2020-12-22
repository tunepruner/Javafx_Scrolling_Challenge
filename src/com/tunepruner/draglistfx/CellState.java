package com.tunepruner.draglistfx;

public enum CellState {
    IS_DRAGGING,
    JUST_DROPPED,
    ON_GRID
    //TODO I should consider moving the animationPermitted method declaration here.
    // I'm also considering making this an abstract class,
    // and making the above listed states to be child classes.
    // Then, each Cell object would have a CellState field.
}
