/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package com.czl.juc.locks.source;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A ReadWriteLock维护一对关联的locks ，一个用于只读操作，一个用于写入。 read lock可以由多个阅读器线程同时进行，只要没有作者。 write lock是独家的。
 *
 * 所有ReadWriteLock实现必须保证的存储器同步效应writeLock操作（如在指定Lock接口）也保持相对于所述相关联的readLock 。 也就是说，一个线程成功获取读锁定将会看到在之前发布的写锁定所做的所有更新。
 *
 * 读写锁允许访问共享数据时的并发性高于互斥锁所允许的并发性。 它利用了这样一个事实：一次只有一个线程（ 写入线程）可以修改共享数据，在许多情况下，任何数量的线程都可以同时读取数据（因此读取器线程）。 从理论上讲，通过使用读写锁允许的并发性增加将导致性能改进超过使用互斥锁。 实际上，并发性的增加只能在多处理器上完全实现，然后只有在共享数据的访问模式是合适的时才可以。
 *
 * 读写锁是否会提高使用互斥锁的性能取决于数据被读取的频率与被修改的频率相比，读取和写入操作的持续时间以及数据的争用 - 即是，将尝试同时读取或写入数据的线程数。 例如，最初填充数据的集合，然后经常被修改的频繁搜索（例如某种目录）是使用读写锁的理想候选。 然而，如果更新变得频繁，那么数据的大部分时间将被专门锁定，并且并发性增加很少。 此外，如果读取操作太短，则读写锁定实现（其本身比互斥锁更复杂）的开销可以支配执行成本，特别是因为许多读写锁定实现仍将序列化所有线程通过小部分代码。 最终，只有剖析和测量将确定使用读写锁是否适合您的应用程序。
 *
 * 虽然读写锁的基本操作是直接的，但是执行必须做出许多策略决策，这可能会影响给定应用程序中读写锁定的有效性。 这些政策的例子包括：
 *
 *     在写入器释放写入锁定时，确定在读取器和写入器都在等待时是否授予读取锁定或写入锁定。 作家偏好是常见的，因为写作预计会很短，很少见。 读者喜好不常见，因为如果读者经常和长期的预期，写作可能导致漫长的延迟。 公平的或“按顺序”的实现也是可能的。
 *     确定在读卡器处于活动状态并且写入器正在等待时请求读取锁定的读取器是否被授予读取锁定。 读者的偏好可以无限期地拖延作者，而对作者的偏好可以减少并发的潜力。
 *     确定锁是否可重入：一个具有写锁的线程是否可以重新获取？ 持有写锁可以获取读锁吗？ 读锁本身是否可重入？
 *     写入锁可以降级到读锁，而不允许插入写者？ 读锁可以升级到写锁，优先于其他等待读者或作者吗？
 *
 * 在评估应用程序的给定实现的适用性时，应考虑所有这些问题
 *
 * A {@code ReadWriteLock} maintains a pair of associated {@link
 * Lock locks}, one for read-only operations and one for writing.
 * The {@link #readLock read lock} may be held simultaneously by
 * multiple reader threads, so long as there are no writers.  The
 * {@link #writeLock write lock} is exclusive.
 *
 * <p>All {@code ReadWriteLock} implementations must guarantee that
 * the memory synchronization effects of {@code writeLock} operations
 * (as specified in the {@link Lock} interface) also hold with respect
 * to the associated {@code readLock}. That is, a thread successfully
 * acquiring the read lock will see all updates made upon previous
 * release of the write lock.
 *
 * <p>A read-write lock allows for a greater level of concurrency in
 * accessing shared data than that permitted by a mutual exclusion lock.
 * It exploits the fact that while only a single thread at a time (a
 * <em>writer</em> thread) can modify the shared data, in many cases any
 * number of threads can concurrently read the data (hence <em>reader</em>
 * threads).
 * In theory, the increase in concurrency permitted by the use of a read-write
 * lock will lead to performance improvements over the use of a mutual
 * exclusion lock. In practice this increase in concurrency will only be fully
 * realized on a multi-processor, and then only if the access patterns for
 * the shared data are suitable.
 *
 * <p>Whether or not a read-write lock will improve performance over the use
 * of a mutual exclusion lock depends on the frequency that the data is
 * read compared to being modified, the duration of the read and write
 * operations, and the contention for the data - that is, the number of
 * threads that will try to read or write the data at the same time.
 * For example, a collection that is initially populated with data and
 * thereafter infrequently modified, while being frequently searched
 * (such as a directory of some kind) is an ideal candidate for the use of
 * a read-write lock. However, if updates become frequent then the data
 * spends most of its time being exclusively locked and there is little, if any
 * increase in concurrency. Further, if the read operations are too short
 * the overhead of the read-write lock implementation (which is inherently
 * more complex than a mutual exclusion lock) can dominate the execution
 * cost, particularly as many read-write lock implementations still serialize
 * all threads through a small section of code. Ultimately, only profiling
 * and measurement will establish whether the use of a read-write lock is
 * suitable for your application.
 *
 *
 * <p>Although the basic operation of a read-write lock is straight-forward,
 * there are many policy decisions that an implementation must make, which
 * may affect the effectiveness of the read-write lock in a given application.
 * Examples of these policies include:
 * <ul>
 * <li>Determining whether to grant the read lock or the write lock, when
 * both readers and writers are waiting, at the time that a writer releases
 * the write lock. Writer preference is common, as writes are expected to be
 * short and infrequent. Reader preference is less common as it can lead to
 * lengthy delays for a write if the readers are frequent and long-lived as
 * expected. Fair, or &quot;in-order&quot; implementations are also possible.
 *
 * <li>Determining whether readers that request the read lock while a
 * reader is active and a writer is waiting, are granted the read lock.
 * Preference to the reader can delay the writer indefinitely, while
 * preference to the writer can reduce the potential for concurrency.
 *
 * <li>Determining whether the locks are reentrant: can a thread with the
 * write lock reacquire it? Can it acquire a read lock while holding the
 * write lock? Is the read lock itself reentrant?
 *
 * <li>Can the write lock be downgraded to a read lock without allowing
 * an intervening writer? Can a read lock be upgraded to a write lock,
 * in preference to other waiting readers or writers?
 *
 * </ul>
 * You should consider all of these things when evaluating the suitability
 * of a given implementation for your application.
 *
 * @see ReentrantReadWriteLock
 * @see Lock
 * @see ReentrantLock
 *
 * @since 1.5
 * @author Doug Lea
 */
public interface ReadWriteLock {
    /**
     * Returns the lock used for reading.
     *
     * @return the lock used for reading
     */
    Lock readLock();

    /**
     * Returns the lock used for writing.
     *
     * @return the lock used for writing
     */
    Lock writeLock();
}
