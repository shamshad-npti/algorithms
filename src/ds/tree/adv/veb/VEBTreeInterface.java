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
public interface VEBTreeInterface {
    public int minimum();
    public int maximum();
    public int successor(int x);
    public int predecessor(int x);
    public boolean insert(int x);
    public boolean delete(int x);
    public int size();
    public boolean contains(int x);
}
