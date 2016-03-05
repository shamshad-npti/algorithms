/*
 * Copyright (C) 2015 shamshad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ds.tree.adv.veb;

/**
 *
 * @author shamshad
 */
public class vEBTree implements VEBTree {
    private final VEBStruct tree = new VEBStruct(16);
    
    @Override
    public int minimum() {
        return tree.min;
    }

    @Override
    public int maximum() {
        return tree.max;
    }

    @Override
    public int successor(int x) {
        return successor(tree, x);
    }

    @Override
    public int predecessor(int x) {
        return predecessor(tree, x);
    }

    @Override
    public boolean insert(int x) {
        return insert(tree, x);
    }

    @Override
    public boolean delete(int x) {
        if(contains(x)) {
            delete(tree, x);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean contains(int x) {
        return contains(tree, x);
    }

    private static void delete(VEBStruct v, int x) {
        if(v.max == v.min) {
            v.max = v.min = -1;
        } else if(v.w == 1) {
            if(x == 0) v.min = 1;
            else v.min = 0;
            v.max = v.min;
        } else {
            if(x == v.min) {
                x = v.min = v.index(v.summary.min, v.clusters[v.summary.min].min);
            }
            delete(v.clusters[v.high(x)], v.low(x));
            if(v.isEmpty()) {
                delete(v.summary, v.high(x));
                if(x == v.max) {
                    int mx = v.summary.max;
                    if(mx == -1)
                        v.max = v.min;
                    else v.max = v.index(mx, v.clusters[mx].max);
                }
            } else if(x == v.max) {
                v.max = v.index(v.high(x), v.clusters[v.high(x)].max);
            }
        }
    }
    
    private static int predecessor(VEBStruct v, int x) {
        if(v.w == 1) {
           if(x == 1 && v.min == 0)
               return 0;
           return -1;
        }
        if(v.max != -1 && x > v.max) return v.max;
        int p = v.high(x);
        if(!v.clusters[p].isEmpty() && v.clusters[p].min < v.low(x)) {
            int offset = predecessor(v.clusters[p], v.low(x));
            return v.index(p, offset);
        } else {
            p = predecessor(v.summary, v.high(x));
            if(p == -1) {
                if(!v.isEmpty() && v.min < x)
                    return v.min;
                return -1;
            }
            return v.index(p, v.clusters[p].max);
        }
    }
    
    private static int successor(VEBStruct v, int x) {
        if(v.w == 1) {
            if(x == 0 && v.max == 1)
                return 1;
            return -1;
        }
        if(x < v.min) return v.min;
        int sucClust = v.high(x);
        if(v.low(x) < v.clusters[sucClust].max) {
            return v.index(sucClust, successor(v.clusters[sucClust], v.low(x)));
        } else {
            sucClust = successor(v.summary, v.high(x));
            if(sucClust != -1) {
                return v.index(sucClust, v.clusters[sucClust].min);
            }
            return -1;
        }
    }
    
    private static boolean insert(VEBStruct v, int x) {
        if(v.min == -1) {
            v.min = v.max = x;
            return true;
        } else {
            boolean inserted = false;
            if(x < v.min) {
                int temp = x;
                x = v.min;
                v.min = temp;
                inserted = true;
            }
            if(v.w > 1) {
                if(v.clusters[v.high(x)].min == -1)
                    insert(v.summary, v.high(x));
                inserted |= insert(v.clusters[v.high(x)], v.low(x));
            }
            if(x > v.max) {
                v.max = x;
                inserted = true;
            }
            return inserted;
        }
    }
    
    private static boolean contains(VEBStruct v, int x) {
        if(v.min == x || v.max == x) return true;
        if(v.w == 1) return false;
        return contains(v.clusters[v.high(x)], v.low(x));
    }
    
    private static class VEBStruct {
        private int w;
        private int w2;
        private int min;
        private int max;
        private VEBStruct summary;
        private VEBStruct[] clusters;

        public VEBStruct(int w) {
            this.w = w;
            this.w2 = w >> 1;
            this.min = this.max = -1;
            if(w > 1) {
                buildChild(w);
            }
        }
        
        private int high(int x) {
            return x >> w2;
        }
        
        private int low(int x) {
            return x & ((1 << w2) - 1);
        }
        
        private int index(int x, int y) {
            return (x << w2) | y;
        }
        
        private void buildChild(int w) {
            int s = 1 << w2;
            this.summary = new VEBStruct((w + 1) >> 1);
            this.clusters = new VEBStruct[s];
            for(int i = 0; i < s; i++) {
                this.clusters[i] = new VEBStruct(w2);
            }
        }
        
        private boolean isEmpty() {
            return this.min == -1;
        }
    }
}
