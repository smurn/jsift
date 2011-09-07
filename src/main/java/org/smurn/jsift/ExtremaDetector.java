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

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Strategy that detects local extrema in a scale-space.
 * The strategy searches all pixels in the difference-of-gaussian images
 * and returns the positions of those who's value is either smaller or larger
 * than all 26 neighboring pixels.
 */
public class ExtremaDetector implements KeypointDetector {

    @Override
    public Collection<ScaleSpacePoint> detectKeypoints(
            final ScaleSpace scaleSpace) {

        if (scaleSpace == null) {
            throw new NullPointerException("scale space must not be null");
        }

        List<ScaleSpacePoint> points = new ArrayList<ScaleSpacePoint>();
        for (Octave octave : scaleSpace.getOctaves()) {

            int dogCount = octave.getDifferenceOfGaussians().size();
            for (int i = 1; i < dogCount - 1; i++) {
                Collection<ScaleSpacePoint> pointsOnThisScale = detectKeypoints(
                        octave.getDifferenceOfGaussians().get(i - 1),
                        octave.getDifferenceOfGaussians().get(i),
                        octave.getDifferenceOfGaussians().get(i + 1));
                points.addAll(pointsOnThisScale);
            }
        }

        return points;
    }

    /**
     * Detects extrema on one scale.
     * @param low   The DoG at one scale lower.
     * @param center The DoG on which to detect the extrema.
     * @param high The DoG at one scale higher.
     * @return The extremas.
     */
    private Collection<ScaleSpacePoint> detectKeypoints(
            Image low, Image center, Image high) {

        List<ScaleSpacePoint> points = new LinkedList<ScaleSpacePoint>();
        for (int row = 1; row < center.getHeight() - 1; row++) {
            for (int col = 1; col < center.getWidth() - 1; col++) {

                float value = center.getPixel(row, col);

                // Since all neighbors all need to be on the same 'side' for an
                // extremum we can just take an arbitrary neighbor to determine
                // if we might face a minimum or a maximum.                
                float sign = Math.signum(value - center.getPixel(row, col - 1));
                if (sign == 0.0f) {
                    break;
                }
                value *= sign;
                boolean isExtremum = true;
                isExtremum &= low.getPixel(row - 1, col - 1) * sign < value;
                isExtremum &= low.getPixel(row - 1, col) * sign < value;
                isExtremum &= low.getPixel(row - 1, col + 1) * sign < value;
                isExtremum &= low.getPixel(row, col - 1) * sign < value;
                isExtremum &= low.getPixel(row, col) * sign < value;
                isExtremum &= low.getPixel(row, col + 1) * sign < value;
                isExtremum &= low.getPixel(row + 1, col - 1) * sign < value;
                isExtremum &= low.getPixel(row + 1, col) * sign < value;
                isExtremum &= low.getPixel(row + 1, col + 1) * sign < value;

                isExtremum &= center.getPixel(row - 1, col - 1) * sign < value;
                isExtremum &= center.getPixel(row - 1, col) * sign < value;
                isExtremum &= center.getPixel(row - 1, col + 1) * sign < value;
                isExtremum &= center.getPixel(row, col - 1) * sign < value;
                isExtremum &= center.getPixel(row, col + 1) * sign < value;
                isExtremum &= center.getPixel(row + 1, col - 1) * sign < value;
                isExtremum &= center.getPixel(row + 1, col) * sign < value;
                isExtremum &= center.getPixel(row + 1, col + 1) * sign < value;

                isExtremum &= high.getPixel(row - 1, col - 1) * sign < value;
                isExtremum &= high.getPixel(row - 1, col) * sign < value;
                isExtremum &= high.getPixel(row - 1, col + 1) * sign < value;
                isExtremum &= high.getPixel(row, col - 1) * sign < value;
                isExtremum &= high.getPixel(row, col) * sign < value;
                isExtremum &= high.getPixel(row, col + 1) * sign < value;
                isExtremum &= high.getPixel(row + 1, col - 1) * sign < value;
                isExtremum &= high.getPixel(row + 1, col) * sign < value;
                isExtremum &= high.getPixel(row + 1, col + 1) * sign < value;

                if (isExtremum) {
                    Point2D coords = center.toOriginal(
                            new Point2D.Double(row, col));
                    ScaleSpacePoint point = new ScaleSpacePoint(
                            coords.getX(), coords.getY(), center.getSigma());
                    points.add(point);
                }
            }
        }

        return points;
    }
}
