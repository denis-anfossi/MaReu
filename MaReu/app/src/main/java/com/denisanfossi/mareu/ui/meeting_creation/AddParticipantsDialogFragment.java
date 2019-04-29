package com.denisanfossi.mareu.ui.meeting_creation;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.denisanfossi.mareu.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.base.Joiner;

import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddParticipantsDialogFragment extends DialogFragment {
    private static final String TAG = "addParticipantsDialogFragment";
    @BindView(R.id.fragment_add_participants_toolbar) Toolbar mToolbar;
    @BindView(R.id.fragment_add_participants_btn) ImageButton mAddParticipantButton;
    @BindView(R.id.fragment_add_participants_text_input) TextInputLayout mAddParticipantTextInput;
    @BindView(R.id.fragment_add_participants_list_text) TextView mAddParticipantsListText;
    private OnFragmentInteractionListener mOnParticipantsSetListener;
    private Set<String> mParticipants;

    public AddParticipantsDialogFragment() {
    }

    public static AddParticipantsDialogFragment display(FragmentManager fragmentManager) {
        AddParticipantsDialogFragment addParticipantsDialogFragment = newInstance();
        addParticipantsDialogFragment.show(fragmentManager, TAG);
        return addParticipantsDialogFragment;
    }

    public static AddParticipantsDialogFragment newInstance() {
        return new AddParticipantsDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_FullScreenDialog);
        mParticipants = new TreeSet<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_participants_dialog, container, false);
        ButterKnife.bind(this, view);
        configureAddParticipantButton();
        configureAddParticipantText();
        return view;
    }

    private void configureAddParticipantText() {
        mAddParticipantTextInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mAddParticipantTextInput.getEditText().getText().length() > 2) {
                    mAddParticipantButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void configureAddParticipantButton() {
        mAddParticipantButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParticipants.add(mAddParticipantTextInput.getEditText().getText().toString());
                setParticipantsList();
                mAddParticipantTextInput.getEditText().setText("");
                mAddParticipantButton.setVisibility(View.GONE);
            }
        });
    }

    private void setParticipantsList() {
        String participants = "Participants list:\n\n".concat(Joiner.on("\n").skipNulls().join(mParticipants));
        if (!participants.equals("Participants list:\n\n")) {
            mAddParticipantsListText.setText(participants);
        } else {
            mAddParticipantsListText.setText("");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setNavigationOnClickListener(v -> dismiss());
        mToolbar.inflateMenu(R.menu.add_participants_dialog);
        mToolbar.setOnMenuItemClickListener(item -> {
            mOnParticipantsSetListener.onSaveAddParticipantsDialogFragment(mParticipants);
            dismiss();
            return true;
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setOnParticipantsSetListener(@NonNull OnFragmentInteractionListener onParticipantsSetListener) {
        mOnParticipantsSetListener = checkNotNull(onParticipantsSetListener);
    }

    public interface OnFragmentInteractionListener {
        void onSaveAddParticipantsDialogFragment(Set<String> participants);
    }
}
