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

/**
 * Set of three neibooring difference-of-gaussian levels.
 * <p>All images have the same width and height.</p>
 * <p>The Number after each image is the scale-exponent offset to
 * {@link #getBaseScaleExponent()}. For example the scale-exponent of
 * {@link #getDoG3()} is {@code getScaleExponent()+3}.</p>
 * <p>The {@code gaussianX} images are the blurred versions of the
 * original image. They correspond to the {@code L} function in Lowe's
 * paper and form the scale-space.<br/>
 * The {@code DoGX} images are the difference of gaussians. For example
 * {@link #getDoG3()} is the difference of {@link #getGaussian2()} and
 * {@link #getGaussian4()}.
 */
public final class ScaleLevel {

    private final Image gaussian0;
    private final Image dog1;
    private final Image gaussian2;
    private final Image dog3;
    private final Image gaussian4;
    private final Image dog5;
    private final Image gaussian6;

    /**
     * Creates an instance.
     * @param gaussian0 Scale-space image with scale offset 0.
     * @param dog1  Difference-of-gaussian image with scale offset 1.
     * @param gaussian2 Scale-space image with scale offset 2.
     * @param dog3 Difference-of-gaussian image with scale offset 3.
     * @param gaussian4 Scale-space image with scale offset 4.
     * @param dog5 Difference-of-gaussian image with scale offset 5.
     * @param gaussian6  Scale-space image with scale offset 6.
     * @throws NullPointerException if one of the images is {@code null}.
     * @throws IllegalArgumentException if not all images are of the same
     * size.
     */
    ScaleLevel(final Image gaussian0,
            final Image dog1,
            final Image gaussian2,
            final Image dog3,
            final Image gaussian4,
            final Image dog5,
            final Image gaussian6) {
        this.gaussian0 = gaussian0;
        this.dog1 = dog1;
        this.gaussian2 = gaussian2;
        this.dog3 = dog3;
        this.gaussian4 = gaussian4;
        this.dog5 = dog5;
        this.gaussian6 = gaussian6;
    }

    /**
     * gets the base scale-exponent.
     * The scales of the images are defined relative to this value.
     * @return Base scale-exponent.
     */
    public int getBaseScaleExponent() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the scale-space image with a scale of
     * {@code getBaseScaleExponent()}.
     * @return Image blurred with sigma according to the scale-exponent.
     */
    public Image getGaussian0() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the difference-of-gaussian image with a scale of
     * {@code getBaseScaleExponent()+1}.
     * @return Difference between {@link #getGaussian0()} and
     * {@link #getGaussian2()}.
     */
    public Image getDoG1() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the scale-space image with a scale of
     * {@code getBaseScaleExponent()+2}.
     * @return Image blurred with sigma according to the scale-exponent.
     */
    public Image getGaussian2() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the difference-of-gaussian image with a scale of
     * {@code getBaseScaleExponent()+3}.
     * @return Difference between {@link #getGaussian2()} and
     * {@link #getGaussian4()}.
     */
    public Image getDoG3() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the scale-space image with a scale of
     * {@code getBaseScaleExponent()+3}.
     * @return Image blurred with sigma according to the scale-exponent.
     */
    public Image getGaussian4() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the difference-of-gaussian image with a scale of
     * {@code getBaseScaleExponent()+5}.
     * @return Difference between {@link #getGaussian4()} and
     * {@link #getGaussian6()}.
     */
    public Image getDoG5() {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * Gets the scale-space image with a scale of
     * {@code #getBaseScaleExponent()+5}.
     * @return Image blurred with sigma according to the scale-exponent.
     */
    public Image getGaussian5() {
        throw new UnsupportedOperationException("not implemented");
    }
}
