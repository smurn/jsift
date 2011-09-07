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

import java.util.Collection;

/**
 * Strategy that detects local extrema in a scale-space.
 * The strategy searches all pixels in the difference-of-gaussian images
 * and returns the positions of those who's value is either smaller or larger
 * than all 26 neighboring pixels.
 */
public class ExtremaDetector implements KeypointDetector {

    @Override
    public Collection<ScaleSpacePoint> detectKeypoints(ScaleSpace scaleSpace) {

        throw new UnsupportedOperationException();

    }
}
