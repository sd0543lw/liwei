package com.nowcoder.model;

import java.util.*;

/**
 * Created by liwei on 17/5/27.
 * 作用：专们用来做视图展示的对象，方便velocity的展示
 */
public class ViewObject {
      private Map<String,Object>  objs=new HashMap<>();
      public void set(String key,Object value){
          objs.put(key,value);
      }
      public Object get(String key){
          return objs.get(key);
      }
}
