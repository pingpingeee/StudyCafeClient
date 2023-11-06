package com.example.gui;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ModelManager
{
	private static ModelManager s_m_manager;
	
	private ConcurrentHashMap<String, Model> m_modelMap;
	
	public static ModelManager getInstance()
	{
		if(s_m_manager == null)
			s_m_manager = new ModelManager();
		
		return s_m_manager;
	}

	private ModelManager()
	{
		m_modelMap = new ConcurrentHashMap<String, Model>();
	}
	
	public boolean hasModel(String _modelClassName)
	{
		return m_modelMap.containsKey(_modelClassName);
	}

	public Model getModel(String _modelClassName)
	{
		if(!m_modelMap.containsKey(_modelClassName))
			return null;
	
		Model model = m_modelMap.get(_modelClassName);
		return model;
	}

	public boolean addModel(String _modelClassName, Model _model)
	{
		if(m_modelMap.containsKey(_modelClassName))
			return false;

		m_modelMap.put(_modelClassName, _model);
		return true;
	}

	public void removeModel(Model _model)
	{
		String modelClassName = _model.getClass().getName();

		if(!m_modelMap.containsKey(modelClassName))
			return;

		m_modelMap.remove(modelClassName);
	}
}
