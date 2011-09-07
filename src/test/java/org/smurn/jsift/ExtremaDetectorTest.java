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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link ExtremaDetector}.
 */
public class ExtremaDetectorTest {

    private ExtremaDetector target;

    @Before
    public void setUp() {
        target = new ExtremaDetector();
    }
    
    @Test(expected=NullPointerException.class)
    public void nullScaleSpace(){
        target.detectKeypoints(null);
    }

    @Test
    public void flat() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}})));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = new ArrayList<ScaleSpacePoint>();
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void positive() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.5, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 9.9f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.6, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.7, 1, 0, 0)));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = Arrays.asList(new ScaleSpacePoint(1, 1, 0.6));
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void negative() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.5, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, -9.9f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.6, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.7, 1, 0, 0)));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = Arrays.asList(new ScaleSpacePoint(1, 1, 0.6));
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void left() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {1.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}})));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = new ArrayList<ScaleSpacePoint>();
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void right() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 1.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}})));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = new ArrayList<ScaleSpacePoint>();
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void top() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}})));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = new ArrayList<ScaleSpacePoint>();
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void bottom() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}})));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = new ArrayList<ScaleSpacePoint>();
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void high() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 0.0f}})));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = new ArrayList<ScaleSpacePoint>();
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void low() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 1.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}})));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = new ArrayList<ScaleSpacePoint>();
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }

    @Test
    public void multiScale() {
        Octave octave = new Octave(Arrays.asList(
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3),
                new Image(3, 3)),
                Arrays.asList(
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.5, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 9.9f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.6, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.7, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, -9.9f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.8, 1, 0, 0),
                new Image(new float[][]{{0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}, {0.0f, 0.0f, 0.0f}}, 0.9, 1, 0, 0)));
        ScaleSpace scaleSpace = new ScaleSpace(Arrays.asList(octave));

        List<ScaleSpacePoint> expected = Arrays.asList(
                new ScaleSpacePoint(1, 1, 0.6),
                new ScaleSpacePoint(1, 1, 0.8));
        Collection<ScaleSpacePoint> actual = target.detectKeypoints(scaleSpace);
        assertEquals(expected, actual);
    }
}
