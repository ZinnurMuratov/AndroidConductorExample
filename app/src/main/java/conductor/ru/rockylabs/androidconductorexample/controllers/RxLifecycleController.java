package conductor.ru.rockylabs.androidconductorexample.controllers;

/**
 * Created by Zinnur on 26.12.16.
 */

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bluelinelabs.conductor.rxlifecycle.ControllerEvent;
import com.bluelinelabs.conductor.rxlifecycle.RxController;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import conductor.ru.rockylabs.androidconductorexample.R;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

// Shamelessly borrowed from the official RxLifecycle demo by Trello and adapted for Conductor Controllers
// instead of Activities or Fragments.
public class RxLifecycleController extends RxController {

    private static final String TAG = "RxLifecycleController";

    @BindView(R.id.tv_title) TextView tvTitle;

    private Unbinder unbinder;
    private boolean hasExited;

    public RxLifecycleController() {
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "Unsubscribing from constructor");
                    }
                })
                .compose(this.<Long>bindUntilEvent(ControllerEvent.DESTROY))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long num) {
                        Log.i(TAG, "Started in constructor, running until onDestroy(): " + num);
                    }
                });
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        Log.i(TAG, "onCreateView() called");

        View view = inflater.inflate(R.layout.controller_rxlifecycle, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvTitle.setText(getResources().getString(R.string.rxlifecycle_title, TAG));

        Observable.interval(1, TimeUnit.SECONDS)
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "Unsubscribing from onCreateView)");
                    }
                })
                .compose(this.<Long>bindUntilEvent(ControllerEvent.DESTROY_VIEW))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long num) {
                        Log.i(TAG, "Started in onCreateView(), running until onDestroyView(): " + num);
                    }
                });

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        Log.i(TAG, "onAttach() called");

        (((ActionBarProvider)getActivity()).getSupportActionBar()).setTitle("RxLifecycle Demo");

        Observable.interval(1, TimeUnit.SECONDS)
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "Unsubscribing from onAttach()");
                    }
                })
                .compose(this.<Long>bindUntilEvent(ControllerEvent.DETACH))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long num) {
                        Log.i(TAG, "Started in onAttach(), running until onDetach(): " + num);
                    }
                });
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);

        Log.i(TAG, "onDestroyView() called");

        unbinder.unbind();
        unbinder = null;
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);

        Log.i(TAG, "onDetach() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy() called");


    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);

        hasExited = !changeType.isEnter;
        if (isDestroyed()) {
        }
    }

    @OnClick(R.id.btn_next_release_view) void onNextWithReleaseClicked() {
        setRetainViewMode(RetainViewMode.RELEASE_DETACH);

        getRouter().pushController(RouterTransaction.with(new TextController("Logcat should now report that the observables from onAttach() and onViewBound() have been unsubscribed from, while the constructor observable is still running."))
                .pushChangeHandler(new HorizontalChangeHandler())
                .popChangeHandler(new HorizontalChangeHandler()));
    }

    @OnClick(R.id.btn_next_retain_view) void onNextWithRetainClicked() {
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);

        getRouter().pushController(RouterTransaction.with(new TextController("Logcat should now report that the observables from onAttach() has been unsubscribed from, while the constructor and onViewBound() observables are still running."))
                .pushChangeHandler(new HorizontalChangeHandler())
                .popChangeHandler(new HorizontalChangeHandler()));
    }
}