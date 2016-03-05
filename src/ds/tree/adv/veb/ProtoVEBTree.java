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
public class ProtoVEBTree implements VEBTree {
    private final ProtoVEBStructure tree = new ProtoVEBStructure(8);
        
    @Override
    public int minimum() {
        return tree.minimum(tree);
    }

    @Override
    public int maximum() {
        return tree.maximum(tree);
    }

    @Override
    public int successor(int x) {
        return tree.successor(tree, x);
    }

    @Override
    public int predecessor(int x) {
        return tree.predecessor(tree, x);
    }

    @Override
    public boolean insert(int x) {
        return tree.insert(tree, x);
    }

    @Override
    public boolean delete(int x) {
        return tree.delete(tree, x);
    }

    @Override
    public int size() {
        return -1;
    }

    @Override
    public boolean contains(int x) {
        return tree.contains(tree, x);
    }
    
    private static class ProtoVEBStructure {
        private int w;
        private int w2;
        private ProtoVEBStructure summary;
        private ProtoVEBStructure[] clusters;

        public ProtoVEBStructure(int w) {
            buildTree(w);
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
        
        private int predecessor(ProtoVEBStructure v, int x) {
            if(v.w == 1) {
                if(x == 1 && v.clusters[0] != null) return 0;
                return -1;
            } else {
                int offset = predecessor(v.clusters[v.high(x)], v.low(x));
                if(offset != -1) {
                    return v.index(v.high(x), offset);
                } 
                int predClust = predecessor(v.summary, v.high(x));
                if(predClust == -1)
                    return -1;
                return v.index(predClust, maximum(v.clusters[predClust]));
            }
        }
        
        private int successor(ProtoVEBStructure v, int x) {
            if(v.w == 1) {
                if(x == 0 && v.clusters[1] != null) {
                    return 1;
                }
                return -1;
            } else {
                int offset = successor(v.clusters[v.high(x)], v.low(x));
                if(offset != -1) {
                    return v.index(v.high(x), offset);
                } else {
                    int sucClust = successor(v.summary, v.high(x));
                    if(sucClust == -1) {
                        return -1;
                    }
                    return v.index(sucClust, minimum(v.clusters[sucClust]));
                }
            }
        }
        
        private int maximum(ProtoVEBStructure v) {
            if(v.w == 1) {
                if(v.clusters[1] != null) return 1;
                else if(v.clusters[0] != null) return 0;
                else return -1;
            } else {
                int maxClust = maximum(v.summary);
                if(maxClust != -1)
                    return v.index(maxClust, maximum(v.clusters[maxClust]));
                return -1;
            }
        }
        
        private int minimum(ProtoVEBStructure v) {
            if(v.w == 1) {
                if(v.clusters[0] != null) return 0;
                else if(v.clusters[1] != null) return 1;
                else return -1;
            } else {
                int minClust = minimum(v.summary);
                if(minClust != -1)
                    return v.index(minClust, minimum(v.clusters[minClust]));
                return -1;
            }
        }
        
        private boolean contains(ProtoVEBStructure v, int x) {
            if(v.w == 1) {
                return v.clusters[x] != null;
            } else {
                return contains(v.clusters[v.high(x)], v.low(x));
            }
        }
        
        private boolean insert(ProtoVEBStructure v, int x) {
            if(v.w == 1) {
                if(v.clusters[x] == null) {
                    v.clusters[x] = new ProtoVEBStructure(0);
                    return v.clusters[1 - x] == null;
                }
                return false;
            } else {
                boolean updated = insert(v.clusters[v.high(x)], v.low(x));
                if(updated) {
                    insert(v.summary, v.high(x));
                }
                return updated;
            }
        }
        
        private boolean delete(ProtoVEBStructure v, int x) {
            if(v.w == 1) {
                if(v.clusters[x] == null) return false;
                v.clusters[x] = null;
                return v.clusters[1 - x] == null;
            } else {
                boolean deleted = delete(v.clusters[v.high(x)], v.low(x));
                if(deleted) {
                    return delete(v.summary, v.high(x));
                }
                return deleted;
            }
        }
        
        private void buildTree(int w) {
            if(w == 1) {
                this.clusters = new ProtoVEBStructure[2];
                this.w = 1;
            } else if(w != 0) {
                this.w = w;
                this.w2 = w >> 1;
                int s = 1 << w2;
                this.summary = new ProtoVEBStructure(w2);
                this.clusters = new ProtoVEBStructure[s];
                for(int i = 0; i < s; i++) {
                    this.clusters[i] = new ProtoVEBStructure(w2);
                }
            }
        }
    }
}
