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
 * Unit tests for {@link Interpolator}.
 */
public class LinearUpScalerTest {

    @Test(expected = NullPointerException.class)
    public void nullImage() {
        LinearUpScaler target = new LinearUpScaler();
        target.upScale(null);
    }

    @Test
    public void zeroImage() {
        LinearUpScaler target = new LinearUpScaler();
        Image actual = target.upScale(new Image(0, 10));
        Image expected = new Image(0, 19);
        assertThat(actual, TestUtils.equalTo(expected, 1E-10f));
    }

    @Test
    public void oneByOne() {
        LinearUpScaler target = new LinearUpScaler();
        Image actual = target.upScale(new Image(new float[][]{{0.4f}}));
        Image expected = new Image(new float[][]{{0.4f}});
        assertThat(actual, TestUtils.equalTo(expected, 1E-10f));
    }

    @Test
    public void sigma() {
        LinearUpScaler target = new LinearUpScaler();
        Image actual = target.upScale(new Image(10, 10, 3.4, 2, 3, 4));
        assertEquals(3.4, actual.getSigma(), 1E-6);
        assertEquals(4.0, actual.getScale(), 1E-6);
        assertEquals(6.0, actual.getOffsetX(), 1E-6);
        assertEquals(8.0, actual.getOffsetY(), 1E-6);
    }

    @Test
    public void interpolate() {
        Image input = new Image(new float[][]{
                    {1.0f, 2.0f, 3.0f},
                    {4.0f, 5.0f, 6.0f}
                });

        Image expected = new Image(new float[][]{
                    {1.0f, 1.5f, 2.0f, 2.5f, 3.0f},
                    {2.5f, 3.0f, 3.5f, 4.0f, 4.5f},
                    {4.0f, 4.5f, 5.0f, 5.5f, 6.0f}
                });

        LinearUpScaler target = new LinearUpScaler();
        Image actual = target.upScale(input);

        assertThat(actual, equalTo(expected, 1E-10f));
    }
}
