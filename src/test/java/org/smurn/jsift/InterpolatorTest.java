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
public class InterpolatorTest {

    @Test(expected = NullPointerException.class)
    public void nullImage() {
        Interpolator target = new Interpolator();
        target.interpolate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroImage() {
        Interpolator target = new Interpolator();
        target.interpolate(new Image(0, 10));
    }

    @Test
    public void oneByOne() {
        Interpolator target = new Interpolator();
        Image actual = target.interpolate(new Image(new float[][]{{0.4f}}));
        Image expected = new Image(new float[][]{{0.4f}});
        assertThat(actual, TestUtils.equalTo(expected, 1E-10f));
    }

    @Test
    public void interpolate() {
        Image input = new Image(new float[][]{
                    {1.0f, 2.0f, 3.0f},
                    {4.0f, 5.0f, 6.0f}
                });

        Image expected = new Image(new float[][]{
                    {1.0f, 1.5f, 2.0f, 2.5f, 3.0f},
                    {3.0f, 3.5f, 4.0f, 4.0f, 5.0f},
                    {4.0f, 4.5f, 5.0f, 5.5f, 6.0f}
                });

        Interpolator target = new Interpolator();
        Image actual = target.interpolate(input);

        assertThat(actual, equalTo(expected, 1E-10f));
    }
}
