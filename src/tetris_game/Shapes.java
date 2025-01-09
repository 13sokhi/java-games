package tetris_game;

import tetris_game.shapes.Shape;
import tetris_game.shapes.*;

public enum Shapes {
    O(O.class),
    I(I.class),
    S(S.class),
    Z(Z.class),
    L(L.class),
    J(J.class),
    T(T.class);

    Class<? extends Shape> shape;

    Shapes(Class<? extends Shape> shape) {
        this.shape = shape;
    }

    public Class<? extends Shape> getShape() {
        return this.shape;
    }
}
