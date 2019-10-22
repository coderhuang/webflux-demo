package com.example.demo.zk;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class ZkTest {

	private static final String CONNECT_STRING = "10.121.201.11:2181,10.121.201.21:2181,"
			+ "10.121.201.31:2181";
	
	private static final int SESSION_TIMEOUT = 30000;
	
	private static final String PARENT_NODE = "/devops_center";
	
	private ZooKeeper zk;
	
	@Before
	public void init() throws IOException {
		
		zk = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, null);
	}
	
	@Test
	public void watchNonExistNode() throws KeeperException, InterruptedException{
		
		String originPath = "/devops-center-ephemeral-test";
		String actualPath = zk.create(originPath, "test".getBytes(), null, CreateMode.EPHEMERAL);
		System.err.println(actualPath + " create success");
		zk.exists(PARENT_NODE, event -> {
			
			if (event.getType().equals(EventType.NodeCreated)) {
				
				String path = event.getPath();
				try {
					System.err.println(zk.exists(path, false));
				} catch (KeeperException | InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		});
	}

	@Test
	public void createZnodeTest() throws InterruptedException {

		ACL acl = new ACL();
		Id id = new Id();
		id.setScheme("digest");
		id.setId("devops-center:123456");
		acl.setId(id);
		
		String createNodePath = PARENT_NODE;
		try {
			String lol = zk.create(PARENT_NODE, "devops-center父节点".getBytes(), Arrays.asList(acl),
					CreateMode.EPHEMERAL_SEQUENTIAL);
			System.err.println(lol);
			
			Stat stat = zk.exists(lol, false);
			if (Objects.nonNull(stat)) {
				
				zk.delete(lol, stat.getVersion());
			}
		} catch (KeeperException e) {
			
			if (e instanceof KeeperException.NoNodeException) {
				
				System.err.println(createNodePath + " => 父路径不存在");
			} else if (e instanceof KeeperException.NoChildrenForEphemeralsException) {
				
				System.err.println(createNodePath + " => 父路径不能是ephemeral节点");
			}
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void existAndDelTest() throws InterruptedException {
		
		String nodePath = "/devops_center0000000019";
		Stat stat;
		try {
			stat = zk.exists(nodePath, false);
			if (Objects.nonNull(stat)) {
				
				zk.delete(nodePath, stat.getVersion());
			} else {
				
				System.err.println(nodePath + "-节点不存在");
			}
		} catch (KeeperException e) {
			
			e.printStackTrace();
		}
	}
	
}
