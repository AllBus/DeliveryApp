package com.tytosoft.delivery.common.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class AlertDialogFragment extends DialogFragment {

	public static AlertDialogFragment newInstance(int title, int message,int index) {
		AlertDialogFragment frag = new AlertDialogFragment();
		Bundle args = new Bundle();
		args.putInt("title", title);
		args.putInt("message", message);
		args.putInt("index",index);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int title = getArguments().getInt("title");
		int message = getArguments().getInt("message");
		final int index= getArguments().getInt("index");

		return new AlertDialog.Builder(getActivity())
			//	.setIcon(R.drawable.alert_dialog_icon)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								Activity act=getActivity();
								if (act instanceof IYesNoListener){
									((IYesNoListener)act).onYes(index);
								}


							}
						}
				)
				.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								Activity act=getActivity();
								if (act instanceof IYesNoListener){
									((IYesNoListener)act).onNo(index);
								}
							}
						}
				)
				.create();
	}

	public interface IYesNoListener {
		void onYes(int index);

		void onNo(int index);
	}

}