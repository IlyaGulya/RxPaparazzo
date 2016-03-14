/*
 * Copyright 2016 Refinería Web
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rx_paparazzo.interactors;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import rx.Observable;
import rx_activity_result.RxActivityResult;

public class TakePhoto extends UseCase<Uri>{
    private Activity activity;

    public TakePhoto with(Activity activity) {
        this.activity = activity;
        return this;
    }

    @Override public Observable<Uri> react() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        return RxActivityResult.startIntent(takePictureIntent, activity)
                .flatMap(result -> {
                    if (result.resultCode() == Activity.RESULT_OK) {
                        return Observable.just(result.data().getData());
                    } else {
                        return oBrokeChain();
                    }
                });
    }
}
