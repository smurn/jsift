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

/**
 * Interface for strategies that can solve a system of three unknowns and 
 * three linear equations.
 */
public interface LinearSolver3 {
    
    /**
     * Solves a system of three unknowns and three equations.
     * @param m Matrix accessed as {@code m[row][col]}.
     * @param b The three constant terms.
     * @return The three unknowns, or {@code null} if the matrix is singular.
     * @throws NullPointerException if {@code m} or {@code b} is {@code null}.
     * @throws IllegalArgumentException if {@code m} is not of size 3x3 or
     * {@code b} is not of length 3.
     */
    public double[] solve(double[][] m, double[] b);
    
}
