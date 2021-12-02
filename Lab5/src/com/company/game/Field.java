package com.company.game;

import java.util.Arrays;

public class Field {
    public int minCount;
    public int maxCount;
    public Hole[] holes;

    public Field() {
        this.holes = new Hole[10];

        holes[0] = new Hole(1);
        holes[1] = new Hole(2);
        holes[2] = new Hole(3);
        holes[3] = new Hole(4);
        holes[4] = new Hole(5);
        holes[5] = new Hole(6);
        holes[6] = new Hole(7);
        holes[7] = new Hole(8);
        holes[8] = new Hole(9);
        holes[9] = new Hole(10);

        holes[0].setPrevNext(holes[5], holes[1]);
        holes[1].setPrevNext(holes[0], holes[2]);
        holes[2].setPrevNext(holes[1], holes[3]);
        holes[3].setPrevNext(holes[2], holes[4]);
        holes[4].setPrevNext(holes[3], holes[9]);
        holes[5].setPrevNext(holes[6], holes[0]);
        holes[6].setPrevNext(holes[7], holes[5]);
        holes[7].setPrevNext(holes[8], holes[6]);
        holes[8].setPrevNext(holes[9], holes[7]);
        holes[9].setPrevNext(holes[4], holes[8]);
    }

    public class Hole {
        public Hole prev;
        public Hole next;
        public int count;

        Hole (Hole prev, Hole next, int count) {
            this.prev = prev;
            this.next = next;
            this.count = count;
        }

        Hole (int count) {
            this.count = count;
        }

        public Hole getPrev() {
            return prev;
        }

        public void setPrev(Hole prev) {
            this.prev = prev;
        }

        public Hole getNext() {
            return next;
        }

        public void setNext(Hole next) {
            this.next = next;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Hole{" +
                    "count=" + count +
                    '}';
        }

        public void setPrevNext(Hole prev, Hole next) {
            setPrev(prev);
            setNext(next);
        }
    }

    public Field move(int holeNumber) {
        Field field = new Field();
        Hole currHole = field.holes[0];
        Hole parentHole = this.holes[0];
        for (int i = 1; i < 10; i++) {
            currHole.setCount(parentHole.getCount());
            currHole = currHole.next;
            parentHole = parentHole.next;
        }

        Hole selected = field.holes[holeNumber-1];

        if (holeNumber < 4 || holeNumber > 7) {
            for (int i = selected.getCount(); i > 0; i--) {
                selected = selected.prev;
                selected.setCount(selected.getCount() + 1);
            }
        } else {
            for (int i = selected.getCount(); i > 0; i--) {
                selected = selected.next;
                selected.setCount(selected.getCount() + 1);
            }
        }
        selected = field.holes[holeNumber-1];
        selected.setCount(0);
        estimate();
        return field;
    }

    private void estimate() {
        for (int i = 0; i < 10; i++) {
            if (holes[i].count == 2 || holes[i].count == 4) {
                if (i < 5) {
                    this.maxCount += holes[i].count;
                } else {
                    this.minCount += holes[i].count;
                }
                holes[i].count = 0;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 5; i < 10; i++) {
            sb.append(holes[i].count).append(" ");
        }
        sb.append(System.lineSeparator());
        for (int i = 0; i < 5; i++) {
            sb.append(holes[i].count).append(" ");
        }

        return System.lineSeparator() +
                "AI count=" + minCount + System.lineSeparator() +
                "Your count=" + maxCount + System.lineSeparator() +
                "Field:" + System.lineSeparator() +
                sb;
    }
}
