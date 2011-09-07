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

import org.junit.Test;
import static org.junit.Assert.*;
import static org.smurn.jsift.TestUtils.*;

/**
 * Unit test for {@link GaussianFilter}.
 */
public class GaussianFilterTest {

    @Test(expected = NullPointerException.class)
    public void nullImage() {
        GaussianFilter target = new GaussianFilter();
        target.filter(null, 1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void reduceGaussian() {
        GaussianFilter target = new GaussianFilter();
        target.filter(new Image(20, 20, 3.0, 2, 3, 4), 2.9);
    }

    /**
     * This test smooths a point source which produces the image
     * of the kernel which we calculate with double accuracy as a reference
     * (using the definition of the gauss kernel).
     */
    @Test
    public void pointSource() {
        double sigma = 6.5;
        double divisor = 2 * sigma * sigma;
        double scale = 1.0 / (divisor * Math.PI);

        Image input = new Image(101, 101);
        input.setPixel(50, 50, 1.0f);

        Image expected = new Image(101, 101);
        for (int row = 0; row < input.getHeight(); row++) {
            for (int col = 0; col < input.getWidth(); col++) {
                double x = col - 50.0;
                double y = row - 50.0;
                double value = scale * Math.exp(-(x * x + y * y) / divisor);
                expected.setPixel(row, col, (float) value);
            }
        }

        GaussianFilter target = new GaussianFilter();
        Image actual = target.filter(input, Math.sqrt(6.5 * 6.5 + 0.5 * 0.5));

        assertThat(actual, equalTo(expected, 1E-5f));
    }

    /**
     * This test smooths a point source which produces the image
     * of the kernel which we calculate with double accuracy as a reference
     * (using the definition of the gauss kernel).
     */
    @Test
    public void pointSourceScaled() {
        double sigma = 6.5;
        double divisor = 2 * sigma * sigma;
        double scale = 1.0 / (divisor * Math.PI);

        Image input = new Image(101, 101, 0.8, 2.0, 0, 0);
        input.setPixel(50, 50, 1.0f);

        Image expected = new Image(101, 101);
        for (int row = 0; row < input.getHeight(); row++) {
            for (int col = 0; col < input.getWidth(); col++) {
                double x = col - 50.0;
                double y = row - 50.0;
                double value = scale * Math.exp(-(x * x + y * y) / divisor);
                expected.setPixel(row, col, (float) value);
            }
        }

        GaussianFilter target = new GaussianFilter();
        Image actual = target.filter(input, 3.3470135942359122721782924135676);

        assertThat(actual, equalTo(expected, 1E-5f));
    }

    /**
     * Blurs an image of constant value. This detects when the over-all
     * value is reduced due to kernel-cutoff. This also detects one
     * or the other possible error in boundary treatment.
     */
    @Test
    public void flat() {
        Image input = new Image(100, 100);
        for (int row = 0; row < input.getHeight(); row++) {
            for (int col = 0; col < input.getWidth(); col++) {
                input.setPixel(row, col, 0.5f);
            }
        }

        GaussianFilter target = new GaussianFilter();
        Image actual = target.filter(input, 4.5);

        assertThat(actual, equalTo(input, 1E-10f));
    }
}
