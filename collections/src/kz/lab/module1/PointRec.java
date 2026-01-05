package kz.lab.module1;

public record PointRec(int x, int y) {

    public static class Builder {
        private int x;
        private int y;

        public PointRec build() {
            return new PointRec(x, y);
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }
    }
}
