package com.example.gui;

/*
 * NOTE:
 * 서버에서 실시간으로 자료를 가져와야 할 경우가 있습니다. 해당 경우에 이 클래스를 상속받아 사용하게 될 예정입니다.
 */
public abstract class Worker implements Runnable
{
	private Model m_model;

	public Worker(Model _model)
	{
		m_model = _model;
	}

	@Override
	public final void run()
	{
		/*
		 * NOTE:
		 * 참조 카운터의 값이 0이 아닐 때까지 반복합니다.
		 * 참조 카운터는 -1을 반환할 수 있습니다. 모델이 생성되었지만, 한 번도 사용되지 않았을 때의 경우입니다.
		 */
		while(m_model.getViewCount() != 0)
		{
			onUpdate();

			try
			{
				/*
				 * NOTE:
				 * deltaTime 밀리초 마다 함수를 실행합니다.
				 * 이 값을 수정하면 프레임 갱신 속도를 조절할 수 있습니다.
				 * deltaTime = 16이라면, 16 밀리초*60 = 960 밀리초, 대략 1초 조금 안 되는 시간입니다.
				 * 따라서 onUpdate() 함수는 1초에 약 60번 실행되게 됩니다.
				 */
				int deltaTime = 16;
				Thread.sleep(deltaTime);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				break;
			}
		}

		onThreadEnd();
		ModelManager.getInstance().removeModel(m_model);
	}

	public void onUpdate() { }
	public void onThreadEnd() { }
}
