package conductor.ru.rockylabs.androidconductorexample.controllers;

/**
 * Created by Zinnur on 26.12.16.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import butterknife.BindView;
import conductor.ru.rockylabs.androidconductorexample.R;
import conductor.ru.rockylabs.androidconductorexample.controllers.base.BaseController;
import conductor.ru.rockylabs.androidconductorexample.util.BundleBuilder;

public class TextController extends BaseController {

    private static final String KEY_TEXT = "TextController.text";

    @BindView(R.id.text_view) TextView textView;

    public TextController(String text) {
        this(new BundleBuilder(new Bundle())
                .putString(KEY_TEXT, text)
                .build()
        );
    }

    public TextController(Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_text, container, false);
    }

    @Override
    public void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        textView.setText(getArgs().getString(KEY_TEXT));
    }

}