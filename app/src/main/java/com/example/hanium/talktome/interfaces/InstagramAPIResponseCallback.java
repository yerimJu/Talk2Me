package com.example.hanium.talktome.interfaces;

import com.example.hanium.talktome.exceptions.InstagramException;
import com.example.hanium.talktome.objects.IGPagInfo;

/**
 * Created by Sayyam on 3/18/16.
 */
public interface InstagramAPIResponseCallback<T> {

    public void onResponse(T responseObject, IGPagInfo pageInfo);

    public void onFailure(InstagramException exception);
}
