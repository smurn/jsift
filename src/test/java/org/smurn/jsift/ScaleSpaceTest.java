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

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link ScaleSpace}.
 */
public class ScaleSpaceTest {

    private Octave dummy1;
    private Octave dummy2;

    public void setup() {
        dummy1 = new Octave(Arrays.asList(
                new Image(new float[][]{{1}}),
                new Image(new float[][]{{2}}),
                new Image(new float[][]{{3}}),
                new Image(new float[][]{{4}})),
                Arrays.asList(
                new Image(new float[][]{{5}}),
                new Image(new float[][]{{6}}),
                new Image(new float[][]{{7}})));
        dummy2 = new Octave(Arrays.asList(
                new Image(new float[][]{{10}}),
                new Image(new float[][]{{20}}),
                new Image(new float[][]{{30}}),
                new Image(new float[][]{{40}})),
                Arrays.asList(
                new Image(new float[][]{{50}}),
                new Image(new float[][]{{60}}),
                new Image(new float[][]{{70}})));
    }

    @Test(expected = NullPointerException.class)
    public void ctrNull() {
        new ScaleSpace(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctrEmpty() {
        new ScaleSpace(new ArrayList<Octave>());
    }

    @Test
    public void getOctaves() {
        ScaleSpace target = new ScaleSpace(Arrays.asList(dummy1, dummy2));
        assertEquals(Arrays.asList(dummy1, dummy2), target.getOctaves());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getOctavesImmutable() {
        ScaleSpace target = new ScaleSpace(Arrays.asList(dummy1, dummy2));
        target.getOctaves().clear();
    }
}
