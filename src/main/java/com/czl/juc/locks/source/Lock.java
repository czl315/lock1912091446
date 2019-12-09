
package com.czl.juc.locks.source;
import java.util.concurrent.TimeUnit;

/**
 * Lock的实现比使用synchronized提供更多扩展的锁操作 方法和语句。
 * 他们允许更灵活的结构，可以有很多不同的属性，可以支持多种相关对象（Condition）
 *
 * 锁是多线程用于控制对共享资源的访问的工具。
 * 通常，锁提供对a的共享资源独占访问:每次只有一个线程可以获取锁而且所有对共享资源的访问首先都需要锁获得。
 * 但是，有些锁可能允许并发访问，例如读锁ReadWriteLock。
 *
 * {@code Lock} implementations provide more extensive locking
 * operations than can be obtained using {@code synchronized} methods
 * and statements.  They allow more flexible structuring, may have
 * quite different properties, and may support multiple associated
 * {@link Condition} objects.
 *
 * <p>A lock is a tool for controlling access to a shared resource by
 * multiple threads. Commonly, a lock provides exclusive access to a
 * shared resource: only one thread at a time can acquire the lock and
 * all access to the shared resource requires that the lock be
 * acquired first. However, some locks may allow concurrent access to
 * a shared resource, such as the read lock of a {@link ReadWriteLock}.
 *
 *
 * <p>The use of {@code synchronized} methods or statements provides
 * access to the implicit monitor lock associated with every object, but
 * forces all lock acquisition and release to occur in a block-structured way:
 * when multiple locks are acquired they must be released in the opposite
 * order, and all locks must be released in the same lexical scope in which
 * they were acquired.
 * synchronized的方法或语句提供了关于每个对象访问隐含的监控锁的用法。
 * 但是强制所有锁获取和释放以块结构的方式发生:
 * 当多个锁被获取时，它们必须以相反的顺序被释放，
 * 并且所有的锁必须在它们被获取的相同的词法范围内被释放。
 *
 * <p>While the scoping mechanism for {@code synchronized} methods
 * and statements makes it much easier to program with monitor locks,
 * and helps avoid many common programming errors involving locks,
 * there are occasions where you need to work with locks in a more
 * flexible way. For example, some algorithms for traversing
 * concurrently accessed data structures require the use of
 * &quot;hand-over-hand&quot; or &quot;chain locking&quot;: you
 * acquire the lock of node A, then node B, then release A and acquire
 * C, then release B and acquire D and so on.  Implementations of the
 * {@code Lock} interface enable the use of such techniques by
 * allowing a lock to be acquired and released in different scopes,
 * and allowing multiple locks to be acquired and released in any
 * order.
 * synchronized方法和语法的作用域机制使程序使用监控锁更容易，
 * 并有助于避免许多涉及锁的常见编程错误，
 * 锁的出现使你需要用到锁的地方更加方便。
 * 例如：一些遍历算法并发的访问数据结构的用法：手递手；链锁：
 * 你获取A节点锁，然后B节点，然后释放A并且获取C，然后释放B获取D等等。
 * Lock接口的实现可以使用这样的技术实现：允许一把锁被获取和释放在不同的作用域里，
 * 并且允许多把锁以某种顺序被获取和释放。
 *
 * <p>With this increased flexibility comes additional
 * responsibility. The absence of block-structured locking removes the
 * automatic release of locks that occurs with {@code synchronized}
 * methods and statements. In most cases, the following idiom
 * should be used:
 *
 *  <pre> {@code
 * Lock l = ...;
 * l.lock();
 * try {
 *   // access the resource protected by this lock
 * } finally {
 *   l.unlock();
 * }}</pre>
 *随着灵活性的增加，还带来了额外的责任。
 *块结构锁的减少消除了使用{@code synchronized}自动释放锁的方法和语句。
 *大多数情况下，下面的固定语法可以被使用：
 *Lock l = ...;
 *l.lock();
 *try {
 *  // access the resource protected by this lock
 *} finally {
 *  l.unlock();
 *}
 *
 * When locking and unlocking occur in different scopes, care must be
 * taken to ensure that all code that is executed while the lock is
 * held is protected by try-finally or try-catch to ensure that the
 * lock is released when necessary.
 * 当锁定和解锁在不同的范围内发生时，
 * 必须小心确保所有的代码在锁被执行的时候被try-finally或try-catch保护，
 * 以确保必要时释放锁。
 *
 * <p>{@code Lock} implementations provide additional functionality
 * over the use of {@code synchronized} methods and statements by
 * providing a non-blocking attempt to acquire a lock ({@link
 * #tryLock()}), an attempt to acquire the lock that can be
 * interrupted ({@link #lockInterruptibly}, and an attempt to acquire
 * the lock that can timeout ({@link #tryLock(long, TimeUnit)}).
 * {@code Lock} 的实现比{@code synchronized}的方法或语句提供了额外的功能，
 * 如提供一个非阻塞尝试获取一把锁{@link #tryLock()}，
 * 一种尝试能被中断的获取锁{@link #lockInterruptibly}，
 * 一种尝试能能超时的获取锁{@link #tryLock(long, TimeUnit)}，
 *
 * <p>A {@code Lock} class can also provide behavior and semantics
 * that is quite different from that of the implicit monitor lock,
 * such as guaranteed ordering, non-reentrant usage, or deadlock
 * detection. If an implementation provides such specialized semantics
 * then the implementation must document those semantics.
 * 一种{@code Lock}累也能提供与隐含监控锁不同的行为和语义，
 * 就像保证订单，非重入语法，死锁检测。
 * 如果实现提供了这种专门的语义，然后实现必须记录这些语义。
 *
 * <p>Note that {@code Lock} instances are just normal objects and can
 * themselves be used as the target in a {@code synchronized} statement.
 * Acquiring the
 * monitor lock of a {@code Lock} instance has no specified relationship
 * with invoking any of the {@link #lock} methods of that instance.
 * It is recommended that to avoid confusion you never use {@code Lock}
 * instances in this way, except within their own implementation.
 * 注意，{@code Lock}实例只是普通的对象，它们可以本身在{@code synchronized}语句中用作目标。
 * 获取实例的监视锁没有指定的关系调用该实例的任何{@link #lock}方法。
 * 为了避免混淆，建议您永远不要使用{@code Lock}实例，除非在它们自己的实现中。
 *
 * <p>Except where noted, passing a {@code null} value for any
 * parameter will result in a {@link NullPointerException} being
 * thrown.
 * 除非特别说明，否则为any传递一个{@code null}值参数将导致{@link NullPointerException}抛出。
 *
 * <h3>Memory Synchronization</h3>
 * 内存同步
 *
 * <p>All {@code Lock} implementations <em>must</em> enforce the same
 * memory synchronization semantics as provided by the built-in monitor
 * lock, as described in
 * <a href="https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4">
 * The Java Language Specification (17.4 Memory Model)</a>:
 * <ul>
 * <li>A successful {@code lock} operation has the same memory
 * synchronization effects as a successful <em>Lock</em> action.
 * <li>A successful {@code unlock} operation has the same
 * memory synchronization effects as a successful <em>Unlock</em> action.
 * </ul>
 * 所有{@code Lock}实现必须强制相同内置监视器提供的内存同步语义锁，
 * 就像https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.4的描述
 * Java语义规范(17.4 内存模型)
 * 成功的{@code lock}操作具有相同的内存同步效果作为一个成功的锁动作。
 * 解锁同样。
 *
 * Unsuccessful locking and unlocking operations, and reentrant
 * locking/unlocking operations, do not require any memory
 * synchronization effects.
 * 不成功的锁定和解锁操作，以及重入锁定/解锁操作，不需要任何内存同步效果。
 *
 * <h3>Implementation Considerations</h3>
 * 实现注意事项
 *
 * <p>The three forms of lock acquisition (interruptible,
 * non-interruptible, and timed) may differ in their performance
 * characteristics, ordering guarantees, or other implementation
 * qualities.  Further, the ability to interrupt the <em>ongoing</em>
 * acquisition of a lock may not be available in a given {@code Lock}
 * class.  Consequently, an implementation is not required to define
 * exactly the same guarantees or semantics for all three forms of
 * lock acquisition, nor is it required to support interruption of an
 * ongoing lock acquisition.  An implementation is required to clearly
 * document the semantics and guarantees provided by each of the
 * locking methods. It must also obey the interruption semantics as
 * defined in this interface, to the extent that interruption of lock
 * acquisition is supported: which is either totally, or only on
 * method entry.
 * 锁获取的三种形式(可中断的、不可中断的和定时的)可能在它们的性能上有所不同特性、订购保证或其他实现品质。
 * 此外，中断正在进行的 的能力获取锁在给定的{@code锁}中可能不可用。
 * 因此，不需要定义实现完全相同的保证或语义的所有三种形式锁的获取，也不需要支持a的中断正在进行的锁获取。
 * 每个锁定方法的实现要清晰的写出文档和语法保证实现。
 * 它必须服从接口中定义的中断语法，在支持锁获取中断的范围内:要么完全支持，要么只支持方法入口。
 *
 * <p>As interruption generally implies cancellation, and checks for
 * interruption are often infrequent, an implementation can favor responding
 * to an interrupt over normal method return. This is true even if it can be
 * shown that the interrupt occurred after another action may have unblocked
 * the thread. An implementation should document this behavior.
 *
 * 由于中断通常意味着取消，并且中断检查通常是少见的，
 * 代码的实现可能更倾向于响应中断而不是正常的方法返回。
 * 即使可以显示在另一个操作之后发生的中断可能已经解除了线程的阻塞，他也是正确的。
 * 文档应该记录这种行为。
 *
 * @see ReentrantLock
 * @see Condition
 * @see ReadWriteLock
 * 参考 重入锁、条件、读写锁
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface Lock {

    /**
     * 获取锁
     * 如果锁不可用，则当前线程将出于线程调度目的而禁用，并处于休眠状态，直到获得锁为止。
     * 实现注意：
     * 代码实现应该能够检测到锁的错误使用，例如导致死锁的调用，并可能在这种情况下抛出(未检查)异常。
     * 情况和异常类型必须由{@code Lock}实现记录。
     * Acquires the lock.
     *
     *
     * <p>If the lock is not available then the current thread becomes
     * disabled for thread scheduling purposes and lies dormant until the
     * lock has been acquired.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>A {@code Lock} implementation may be able to detect erroneous use
     * of the lock, such as an invocation that would cause deadlock, and
     * may throw an (unchecked) exception in such circumstances.  The
     * circumstances and the exception type must be documented by that
     * {@code Lock} implementation.
     */
    void lock();

    /**
     * 获取锁，除非当前线程被中断。
     * 如果锁可用，则获取锁并立即返回。
     * 如果锁不可用，则当前线程将出于线程调度目的而禁用，并处于休眠状态，直到休眠到会发生以下两种情况之一:
     * -当前线程获取到了锁
     * -其他线程中断当前线程，并且锁的获取支持了中断。
     *     * 如果当前线程：
     *      *      已经被它的中断状态设置到它方法的实体里；或者当获取锁时被中断，并且锁已支持中断。
     *      * 当中断异常抛出时，并且当前线程的中断状态已被清除。
     * 实现注意：
     * 中断一个锁获取的能力在一些实现中可能不可用，如果可用，它可能是一个昂贵的操作。
     * 程序员应该意识到这种情况。
     * 代码实现应该记录此情况。
     *
     * Acquires the lock unless the current thread is
     * {@linkplain Thread#interrupt interrupted}.
     *
     * <p>Acquires the lock if it is available and returns immediately.
     *
     * <p>If the lock is not available then the current thread becomes
     * disabled for thread scheduling purposes and lies dormant until
     * one of two things happens:
     *
     * <ul>
     * <li>The lock is acquired by the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of lock acquisition is supported.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while acquiring the
     * lock, and interruption of lock acquisition is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The ability to interrupt a lock acquisition in some
     * implementations may not be possible, and if possible may be an
     * expensive operation.  The programmer should be aware that this
     * may be the case. An implementation should document when this is
     * the case.
     *
     * <p>An implementation can favor responding to an interrupt over
     * normal method return.
     * 实现可能更倾向于响应中断而不是正常的方法返回。
     *
     * <p>A {@code Lock} implementation may be able to detect
     * erroneous use of the lock, such as an invocation that would
     * cause deadlock, and may throw an (unchecked) exception in such
     * circumstances.  The circumstances and the exception type must
     * be documented by that {@code Lock} implementation.
     * 代码实现应该能够检测到锁的错误使用，例如导致死锁的调用，并可能在这种情况下抛出(未检查)异常。
     * 这种情况和异常类型必须由Lock的实现记录下来。
     *
     * @throws InterruptedException if the current thread is
     *         interrupted while acquiring the lock (and interruption
     *         of lock acquisition is supported)
     *  抛出 中断异常：如果当前线程获取锁时被中断（并且锁获取支持了中断）
     */
    void lockInterruptibly() throws InterruptedException;

    /**
     * 只有在调用时是自由无锁的时候，才能获取锁
     * 如果可以获取锁，马上返回true
     * 如果不可以获取锁，马上返回 false。
     * 下面是通常使用的用法习惯：
     *  <pre> {@code
     * Lock lock = ...;
     * if (lock.tryLock()) {
     *   try {
     *     // manipulate protected state
     *   } finally {
     *     lock.unlock();
     *   }
     * } else {
     *   // perform alternative actions
     * }}</pre>
     * 这个用法确认当锁是无锁才会获取锁，并且如果锁没有获取到，不会尝试去解锁。
     *
     * Acquires the lock only if it is free at the time of invocation.
     *
     * <p>Acquires the lock if it is available and returns immediately
     * with the value {@code true}.
     * If the lock is not available then this method will return
     * immediately with the value {@code false}.
     *
     * <p>A typical usage idiom for this method would be:
     *  <pre> {@code
     * Lock lock = ...;
     * if (lock.tryLock()) {
     *   try {
     *     // manipulate protected state
     *   } finally {
     *     lock.unlock();
     *   }
     * } else {
     *   // perform alternative actions
     * }}</pre>
     *
     * This usage ensures that the lock is unlocked if it was acquired, and
     * doesn't try to unlock if the lock was not acquired.
     *
     * @return {@code true} if the lock was acquired and
     *         {@code false} otherwise
     */
    boolean tryLock();

    /**
     * 带有给出的等待时间并且当前线程未被中断时获取锁。
     * 如果锁可用，马上返回ture。
     * 如果锁不可用，当前线程会被调度未禁用，且沉睡，知道下面三种情况出现：
     * 1、当前线程获取到锁。
     * 2、其他线程中断当前线程，并且锁支持了中断。
     * 3、设定的时间到期了。
     * 如果获取到锁，那么返回true。
     *
     * Acquires the lock if it is free within the given waiting time and the
     * current thread has not been {@linkplain Thread#interrupt interrupted}.
     *
     * <p>If the lock is available this method returns immediately
     * with the value {@code true}.
     * If the lock is not available then
     * the current thread becomes disabled for thread scheduling
     * purposes and lies dormant until one of three things happens:
     * <ul>
     * <li>The lock is acquired by the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
     * current thread, and interruption of lock acquisition is supported; or
     * <li>The specified waiting time elapses
     * </ul>
     *
     * <p>If the lock is acquired then the value {@code true} is returned.
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while acquiring
     * the lock, and interruption of lock acquisition is supported,
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then the value {@code false}
     * is returned.
     * If the time is
     * less than or equal to zero, the method will not wait at all.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The ability to interrupt a lock acquisition in some implementations
     * may not be possible, and if possible may
     * be an expensive operation.
     * The programmer should be aware that this may be the case. An
     * implementation should document when this is the case.
     *
     * <p>An implementation can favor responding to an interrupt over normal
     * method return, or reporting a timeout.
     *
     * <p>A {@code Lock} implementation may be able to detect
     * erroneous use of the lock, such as an invocation that would cause
     * deadlock, and may throw an (unchecked) exception in such circumstances.
     * The circumstances and the exception type must be documented by that
     * {@code Lock} implementation.
     *
     * @param time the maximum time to wait for the lock
     * @param unit the time unit of the {@code time} argument
     * @return {@code true} if the lock was acquired and {@code false}
     *         if the waiting time elapsed before the lock was acquired
     *
     * @throws InterruptedException if the current thread is interrupted
     *         while acquiring the lock (and interruption of lock
     *         acquisition is supported)
     */
    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    /**
     * Releases the lock.
     * 释放锁
     * 实现注意：
     *  代码的实现通常强制实施由那个线程能释放一把锁（只有锁特定的持有者能释放锁），
     *  并且如果违反限制将会抛出未检查异常。
     *  任何的限制和异常类型必须由锁的实现类来标记。
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>A {@code Lock} implementation will usually impose
     * restrictions on which thread can release a lock (typically only the
     * holder of the lock can release it) and may throw
     * an (unchecked) exception if the restriction is violated.
     * Any restrictions and the exception
     * type must be documented by that {@code Lock} implementation.
     */
    void unlock();

    /**
     * Returns a new {@link Condition} instance that is bound to this
     * {@code Lock} instance.
     *
     * <p>Before waiting on the condition the lock must be held by the
     * current thread.
     * A call to {@link Condition#await()} will atomically release the lock
     * before waiting and re-acquire the lock before the wait returns.
     *
     * <p><b>Implementation Considerations</b>
     *
     * <p>The exact operation of the {@link Condition} instance depends on
     * the {@code Lock} implementation and must be documented by that
     * implementation.
     *
     * @return A new {@link Condition} instance for this {@code Lock} instance
     * @throws UnsupportedOperationException if this {@code Lock}
     *         implementation does not support conditions
     */
    Condition newCondition();
}
