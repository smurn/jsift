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

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.DecompositionSolver;
import org.apache.commons.math.linear.LUDecomposition;
import org.apache.commons.math.linear.LUDecompositionImpl;
import org.apache.commons.math.linear.NonSquareMatrixException;
import org.apache.commons.math.linear.RealMatrix;

/**
 * Solves a system of three linear equations in three unknowns using the LU
 * decompostion implementation of Apache Commons.
 */
public class LUSolver3 implements LinearSolver3 {

    @Override
    public double[] solve(double[][] m, double[] b) {
        if (m == null || b == null) {
            throw new NullPointerException();
        }
        if (b.length != 3) {
            throw new IllegalArgumentException();
        }
        if (m.length != 3) {
            throw new IllegalArgumentException();
        }
        try {
            RealMatrix matrix = new Array2DRowRealMatrix(m);
            LUDecomposition lu = new LUDecompositionImpl(matrix);
            DecompositionSolver solver = lu.getSolver();
            if (!solver.isNonSingular()) {
                return null;
            }
            return solver.solve(b);
        } catch (NonSquareMatrixException e) {
            throw new IllegalArgumentException();
        }
    }
}
