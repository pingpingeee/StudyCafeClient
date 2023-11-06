package com.example.gui;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AndroidView extends AppCompatActivity
{
	// NOTE: Window.dispose() 함수가 호출되면 이 함수가 실행됩니다.
	public void windowClosed()
	{
		// NOTE: 참조 카운터를 감소시키는 로직 적용
		Model model = this.getModel();

		if(model != null)
			model.unregisterView(this);
	}

	public abstract Model getModel();
}
