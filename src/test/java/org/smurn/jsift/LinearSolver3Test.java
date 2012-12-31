/*
 * Copyright 2012 Stefan C. Mueller.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smurn.jsift;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link LinearSolver3}.
 */
public abstract class LinearSolver3Test {
    
    public abstract LinearSolver3 newTarget();

    @Test(expected = NullPointerException.class)
    public void nullMatrix() {
        LinearSolver3 target = newTarget();
        target.solve(null, new double[3]);
    }

    @Test(expected = NullPointerException.class)
    public void nullConstants() {
        LinearSolver3 target = newTarget();
        target.solve(new double[3][3], null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooFewRows() {
        LinearSolver3 target = newTarget();
        target.solve(new double[2][3], new double[3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooFewColumns() {
        LinearSolver3 target = newTarget();
        target.solve(new double[3][4], new double[3]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooFewConstants() {
        LinearSolver3 target = newTarget();
        target.solve(new double[3][3], new double[2]);
    }

    @Test
    public void identity() {
        LinearSolver3 target = newTarget();
        double[] actual = target.solve(new double[][]{
                    {1, 0, 0},
                    {0, 1, 0},
                    {0, 0, 1}}, new double[]{
                    1,
                    2,
                    3});
        double[] expected = new double[]{
                    1,
                    2,
                    3};
        assertArrayEquals(expected, actual, 1E-8);
    }
   
    @Test
    public void positiveDefinite(){
        LinearSolver3 target = newTarget();
        double[] actual = target.solve(new double[][]{
                    {20, 14,  5},
                    {14, 17,  6},
                    { 5,  6, 19}}, new double[]{
                    10,
                    5,
                    8});
        double[] expected = new double[]{
                    0.691896338955162,
                    -0.405183052241876,
                     0.366927190456602};
        assertArrayEquals(expected, actual, 1E-8);
    }
    
    @Test
    public void negativeDefinite(){
        LinearSolver3 target = newTarget();
        double[] actual = target.solve(new double[][]{
                    {-20, -14,  -5},
                    {-14, -17,  -6},
                    { -5,  -6, -19}}, new double[]{
                    10,
                    5,
                    8});
        double[] expected = new double[]{
                    -0.691896338955163,
                    0.405183052241876,
                     -0.366927190456602};
        assertArrayEquals(expected, actual, 1E-8);
    }

    @Test()
    public void singular(){
        LinearSolver3 target = newTarget();
        double[] actual = target.solve(new double[][]{
                    {1.800758798964840, 1.622471386489725, 0.430224961116444},
                    {1.622471386489725, 1.490561434054626, 0.616016028306392},
                    {0.430224961116444, 0.616016028306392, 1.918586485303430}}, 
                new double[]{
                    10,
                    5,
                    8});
        assertNull(actual);
    }
}
