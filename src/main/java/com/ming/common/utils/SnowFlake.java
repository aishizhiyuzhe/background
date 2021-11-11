package com.ming.common.utils;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @Author: Ming
 * @Description:ѩ���㷨������ȷ��Ψһֵ
 * @Date: Created in 2021/11/11
 * @Modified By:
 */
@Service
@Lazy(value = false)
public class SnowFlake {
    // ��ʼ��ʱ���
    private final static long START_STMP = 1480166465631L;
    // ÿһ����ռ�õ�λ����������
    private final static long SEQUENCE_BIT = 12;// ���к�ռ�õ�λ��
    private final static long MACHINE_BIT = 5; // ������ʶռ�õ�λ��
    private final static long DATACENTER_BIT = 5;// ��������ռ�õ�λ��
    // ÿһ�������ֵ
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);
    // ÿһ���������λ��
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;
    private static long datacenterId; // ��������
    private static long machineId; // ������ʶ
    private static long sequence = 0L; // ���к�
    private static long  lastStmp = -1L;// ��һ��ʱ���

    public SnowFlake(){
        this(1,1);
    }
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }
    //������һ��ID
    public static synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //if�������ʾ��ǰ���ú���һ�ε�����������ͬ�����ڣ�ֻ��ͨ���������֣����к��������ж�ΪΨһ������+1.
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //ͬһ������������Ѿ��ﵽ���ֻ�ܵȴ���һ������
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //��ͬ�����ڣ����к���Ϊ0
            //ִ�е������֧��ǰ����currTimestamp > lastTimestamp��˵�����ε��ø��ϴε��öԱȣ��Ѿ�����ͬһ���������ˣ����ʱ����ſ������»���0�ˡ�
            sequence = 0L;
        }

        lastStmp = currStmp;
        //��������Ժ�����������ID���������ƴ��
        return (currStmp - START_STMP) << TIMESTMP_LEFT //ʱ�������
                | datacenterId << DATACENTER_LEFT      //�������Ĳ���
                | machineId << MACHINE_LEFT            //������ʶ����
                | sequence;                            //���кŲ���
    }

    private static long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private static long getNewstmp() {
        return System.currentTimeMillis();
    }


}
