package com.guizhenyu.netty.common.dispatcher;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * description: MessageHandlerContainer date: 2021/6/3 9:06 下午
 *
 * @author: guizhenyu
 */
@Slf4j
@Component
public class MessageHandlerContainer implements InitializingBean{

  /**
   * 消息类型和消息处理器的映射
   */
  private final Map<String, MessageHandler> handlers = new HashMap<>();


  @Autowired
  private ApplicationContext applicationContext;

  @Override
  public void afterPropertiesSet() throws Exception {
    // 通过 applicationContext 获得所有 MessageHandler Bean
    applicationContext.getBeansOfType(MessageHandler.class).values() // 获得所有 MessageHandler Bean
      .forEach(messageHandler -> handlers.put(messageHandler.getType(), messageHandler));
    log.info("[afterPropertiesSet][消息处理器数量：{}]", handlers.size());
  }


  /**
   * 获取对应的 MessageHandler
   *
   * @param type
   * @return
   */
  MessageHandler getMessageHandler(String type){
    MessageHandler messageHandler = handlers.get(type);
    if (null == messageHandler){
      throw new IllegalArgumentException(String.format("类型(%s) 找不到匹配的 MessageHandler 处理器", type));
    }
    return messageHandler;
  }


  /**
   * 获得 MessageHandler 处理的消息类
   * @param messageHandler
   * @return
   */
  static Class<? extends Message> getMessageClass(MessageHandler messageHandler){
    // 获得 Bean 对应的 Class 类名。 因为可能被AOP代理过
    Class<?> targetClass = AopProxyUtils.ultimateTargetClass(messageHandler);

    // 获得接口的 Type 数组
    Type[] interfaces = targetClass.getGenericInterfaces();
    Class<?> superclass = targetClass.getSuperclass();
    while ((Objects.isNull(interfaces) || 0 == interfaces.length) &&
      Objects.nonNull(superclass)){
      interfaces = superclass.getInterfaces();
//      superclass = targetClass.getSuperclass();
    }

    if (Objects.nonNull(interfaces)){
      for (Type type : interfaces) {
        // 要求 type 是泛型参数
        if (type instanceof ParameterizedType){
          ParameterizedType parameterizedType = (ParameterizedType)type;
          // 要求是 MessageHandler 接口
          if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)){
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            // 取首个元素
            if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0){
              return (Class<Message>) actualTypeArguments[0];
            }else{
              throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", messageHandler));
            }
          }
        }
      }
    }
    throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", messageHandler));
  }

}
