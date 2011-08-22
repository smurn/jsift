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

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.concurrent.Semaphore;

/**
 * Image Observer that allows waiting for the image to complete.
 */
class BlockingImageObserver implements ImageObserver {

    private final Semaphore semaphore = new Semaphore(0);

    @Override
    public boolean imageUpdate(final Image img, final int infoflags,
            final int x, final int y, final int width, final int height) {

        if ((infoflags & ImageObserver.ABORT) == ImageObserver.ABORT
                || (infoflags & ImageObserver.ERROR) == ImageObserver.ERROR
                || (infoflags & ImageObserver.ALLBITS)
                == ImageObserver.ALLBITS) {
            semaphore.release();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Blocks until the image is complete or the thread is interrupted.
     */
    public void waitForImageToComplete() {
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            return;
        }
    }
}
