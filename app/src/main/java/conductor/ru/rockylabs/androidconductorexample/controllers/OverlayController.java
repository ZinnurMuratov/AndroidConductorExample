package conductor.ru.rockylabs.androidconductorexample.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import conductor.ru.rockylabs.androidconductorexample.R;
import conductor.ru.rockylabs.androidconductorexample.controllers.base.BaseController;
import conductor.ru.rockylabs.androidconductorexample.util.BundleBuilder;

/**
 * Created by Zinnur on 26.12.16.
 */

public class OverlayController extends BaseController {

    private static final String KEY_TEXT = "OverlayController.text";

    @BindView(R.id.text_view) TextView textView;

    public OverlayController(CharSequence text) {
        this(new BundleBuilder(new Bundle())
                .putCharSequence(KEY_TEXT, text)
                .build());
    }

    public OverlayController(Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_overlay, container, false);
    }

    @Override
    public void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        textView.setText(getArgs().getCharSequence(KEY_TEXT));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}