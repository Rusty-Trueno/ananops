package com.ananops.core.registry;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.ananops.config.properties.AliyunProperties;
import com.ananops.config.properties.PaascloudProperties;
import com.ananops.config.properties.ZookeeperProperties;
import com.ananops.core.generator.IncrementIdGenerator;
import com.ananops.core.registry.base.CoordinatorRegistryCenter;
import com.ananops.core.registry.base.RegisterDto;
import com.ananops.core.registry.zookeeper.ZookeeperRegistryCenter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心工厂.
 *
 * @author zhangliang
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegistryCenterFactory {

	private static final ConcurrentHashMap<HashCode, CoordinatorRegistryCenter> REG_CENTER_REGISTRY = new ConcurrentHashMap<>();

	/**
	 * 创建注册中心.
	 *
	 * @param zookeeperProperties the zookeeper properties
	 *
	 * @return 注册中心对象 coordinator registry center
	 */
	public static CoordinatorRegistryCenter createCoordinatorRegistryCenter(ZookeeperProperties zookeeperProperties) {
		Hasher hasher = Hashing.md5().newHasher().putString(zookeeperProperties.getZkAddressList(), Charsets.UTF_8);
		HashCode hashCode = hasher.hash();
		CoordinatorRegistryCenter result = REG_CENTER_REGISTRY.get(hashCode);
		if (null != result) {
			return result;
		}
		result = new ZookeeperRegistryCenter(zookeeperProperties);
		result.init();
		REG_CENTER_REGISTRY.put(hashCode, result);
		return result;
	}

	/**
	 * Startup.
	 *
	 * @param paascloudProperties the paascloud properties
	 * @param host                the host
	 * @param app                 the app
	 */
	public static void startup(PaascloudProperties paascloudProperties, String host, String app) {
		//初始化用于协调分布式服务的注册中心
		CoordinatorRegistryCenter coordinatorRegistryCenter = createCoordinatorRegistryCenter(paascloudProperties.getZk());
		RegisterDto dto = new RegisterDto(app, host, coordinatorRegistryCenter);
		//生成分布式ID
		Long serviceId = new IncrementIdGenerator(dto).nextId();
		IncrementIdGenerator.setServiceId(serviceId);
		//当前启动服务（生产者或消费者）注册到zookeeper中心
		registerMq(paascloudProperties, host, app);
	}
	//注册rocketmq生产者消费者
	private static void registerMq(PaascloudProperties paascloudProperties, String host, String app) {
		CoordinatorRegistryCenter coordinatorRegistryCenter = createCoordinatorRegistryCenter(paascloudProperties.getZk());
		AliyunProperties.RocketMqProperties rocketMq = paascloudProperties.getAliyun().getRocketMq();
		//生产者和消费者
		String consumerGroup = rocketMq.isReliableMessageConsumer() ? rocketMq.getConsumerGroup() : null;
		String namesrvAddr = rocketMq.getNamesrvAddr();
		String producerGroup = rocketMq.isReliableMessageProducer() ? rocketMq.getProducerGroup() : null;
		coordinatorRegistryCenter.registerMq(app, host, producerGroup, consumerGroup, namesrvAddr);
	}
}
