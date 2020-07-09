package ndk.utils_android17;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.util.Pair;

import java.util.Objects;

import ndk.utils_android1.ActivityUtils;
import ndk.utils_android1.ActivityWithContexts;
import ndk.utils_android1.DisplayHelper;

public abstract class LaunchersActivity extends ActivityWithContexts {

    //TODO : Fix Margins of buttons - left, right, top : first & others, bottom : first & others
    //TODO : Stroke for button
    //TODO : Typography for button
    //TODO : Fix Height of button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchers);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        add_buttons(constraintLayout, configureLaunchers());
    }

    protected abstract Pair[] configureLaunchers();


    private void add_buttons(ConstraintLayout constraintLayout, Pair[] buttons) {

        int i = 0;
        Button previous_button = null;
        for (final Pair button_item : buttons) {

            // Create Button Dynamically
            Button button = new Button(this);
            button.setText(Objects.requireNonNull(button_item.first).toString());
            button.setId(View.generateViewId());

            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, DisplayHelper.dpToPixel(48, getApplicationContext()));
            button.setLayoutParams(layoutParams);

            button.setOnClickListener(v -> {
                ActivityUtils.startActivityForClass(currentActivityContext, (Class) button_item.second);
            });

            // Add Button to LinearLayout
            constraintLayout.addView(button);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(Objects.requireNonNull(constraintLayout));

            if (i == 0) {
                constraintSet.connect(button.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, 16);
            } else {
                constraintSet.connect(button.getId(), ConstraintSet.TOP, previous_button.getId(), ConstraintSet.BOTTOM, 16);
            }
            constraintSet.connect(button.getId(), ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 16);
            constraintSet.connect(button.getId(), ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 16);

            constraintSet.applyTo(constraintLayout);
            previous_button = button;
            i++;
        }
    }
}
