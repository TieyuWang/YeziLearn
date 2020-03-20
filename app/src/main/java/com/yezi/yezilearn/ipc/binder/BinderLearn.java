package com.yezi.yezilearn.ipc.binder;

/**
 * @author : yezi
 * @date : 2020/3/19 10:55
 * desc   :
 * version: 1.0
 */
public class BinderLearn {

    /**
     * IBinder : IBinder 是一个接口，代表了一种跨进程通信的能力。只要实现了这个借口，这个对象就能跨进程传输。
     *
     *
     * IInterface :  IInterface 代表的就是 Server 进程对象具备什么样的能力（能提供哪些方法，其实对应的就是 AIDL 文件中定义的接口）
     *
     *
     * Binder : Java 层的 Binder 类，代表的其实就是 Binder 本地对象。BinderProxy 类是 Binder 类的一个内部类，
     *         它代表远程进程的 Binder 对象的本地代理；这两个类都继承自 IBinder, 因而都具有跨进程传输的能力；
     *          实际上，在跨越进程的时候，Binder 驱动会自动完成这两个对象的转换。
     *
     *
     * Stub : AIDL 的时候，编译工具会给我们生成一个名为 Stub 的静态内部类；这个类继承了 Binder,
     *          说明它是一个 Binder 本地对象，它实现了 IInterface 接口，表明它具有 Server 承诺给 Client 的能力；
     *          Stub 是一个抽象类，具体的 IInterface 的相关实现需要开发者自己实现。
     *
     * 作者：张磊BARON
     * 链接：https://juejin.im/post/5acccf845188255c3201100f
     * 来源：掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     *
     *
     */
}
