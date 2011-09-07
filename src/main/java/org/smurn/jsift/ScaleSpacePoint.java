/*
 * Copyright 2011 Stefan C. Mueller.
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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Describes a point in the scale-space.
 */
public final class ScaleSpacePoint {

    /** x-coordinate. */
    private final double x;
    /** y-coordinate. */
    private final double y;
    /** scale-coordinate. */
    private final double sigma;

    /**
     * Creates an instance.
     * @param x pixel-centric coordinate in the original image.
     * @param y pixel-centric coordinate in the original image.
     * @param sigma Scale coordinate.
     */
    public ScaleSpacePoint(double x, double y, double sigma) {
        this.x = x;
        this.y = y;
        this.sigma = sigma;
    }

    /**
     * Scale of this point.
     * @return Scale of this point.
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * Pixel-centric x coordinate in the original image.
     * @return Pixel-centric x coordinate in the original image.
     */
    public double getX() {
        return x;
    }

    /**
     * Pixel-centric x coordinate in the original image.
     * @return Pixel-centric x coordinate in the original image.
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(x=" + x + " y=" + y + " s=" + sigma + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ScaleSpacePoint other = (ScaleSpacePoint) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(x, other.x);
        builder.append(y, other.y);
        builder.append(sigma, other.sigma);
        return builder.build();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder(65, 15);
        builder.append(x);
        builder.append(y);
        builder.append(sigma);
        return builder.toHashCode();
    }
}
