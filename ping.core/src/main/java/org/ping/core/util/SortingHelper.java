package org.ping.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * typename: SortingHelper 
 * filename: SortingHelper.java 
 * description: 简单排序工具，所有方法默认均按升序排序
 * 一、稳定性:
	稳定：冒泡排序、插入排序、归并排序和基数排序
	不稳定：选择排序、快速排序、希尔排序、堆排序
       二、平均时间复杂度
	O(n^2):直接插入排序，简单选择排序，冒泡排序。
	在数据规模较小时（9W内），直接插入排序，简单选择排序差不多。当数据较大时，冒泡排序算法的时间代价最高，
	性能为O(n^2)的算法基本上是相邻元素进行比较，基本上都是稳定的。
	O(nlogn):快速排序，归并排序，希尔排序，堆排序。
	其中，快排是最好的， 其次是归并和希尔，堆排序在数据量很大时效果明显。
       三、排序算法的选择
	1.数据规模较小
	（1）待排序列基本序的情况下，可以选择直接插入排序；
	（2）对稳定性不作要求宜用简单选择排序，对稳定性有要求宜用插入或冒泡
　　2.数据规模不是很大
	（1）完全可以用内存空间，序列杂乱无序，对稳定性没有要求，快速排序，此时要付出log（N）的额外空间。
	（2）序列本身可能有序，对稳定性有要求，空间允许下，宜用归并排序
　　3.数据规模很大
	（1）对稳定性有求，则可考虑归并排序。
	（2）对稳定性没要求，宜用堆排序
　　4.序列初始基本有序（正序），宜用直接插入，冒泡
 * date: 2014年10月16日下午9:20:14
 * auth: ping
 */
public class SortingHelper {

	/**
	 * 
	 * method: insertionSort
	 * description: 直接插入排序
	 * date: 2014年10月16日下午9:23:37
	 * auth: ping
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Comparable<T>[] insertionSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			for (int index = 1; index < data.length; index++) {
				// 待插入元素
				Comparable<T> temp = data[index];
				int position = index;
				// 比较，插入位置
				while (position > 0
						&& data[position - 1].compareTo((T) temp) > 0) {
					data[position] = data[position - 1];
					position--;
				}
				data[position] = temp;
			}
		}
		return data;
	}

	/**
	 * 
	 * method: binaryInsertionSort
	 * description: 二分插入排序
	 * date: 2014年10月16日下午9:51:17
	 * auth: ping
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Comparable<T>[] binaryInsertionSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			for (int i = 0; i < data.length; i++) {
				Comparable<T> temp = data[i];
				int left = 0;
				int right = i - 1;
				int mid = 0;
				while (left <= right) {
					mid = (left + right) / 2;
					if (temp.compareTo((T) data[mid]) < 0) {
						right = mid - 1;
					} else {
						left = mid + 1;
					}
				}
				for (int j = i - 1; j >= left; j--) {
					data[j + 1] = data[j];
				}
				if (left != i) {
					data[left] = temp;
				}
			}
		}
		return data;
	}

	/**
	 * 
	 * method: shellSort
	 * description: 希尔排序
	 * date: 2014年10月16日下午9:55:50
	 * auth: ping
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Comparable<T>[] shellSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			int len = data.length;
			while (true) {
				len = len / 2;
				for (int x = 0; x < len; x++) {
					for (int i = x + len; i < data.length; i = i + len) {
						Comparable<T> temp = data[i];
						int j;
						for (j = i - len; j >= 0
								&& data[j].compareTo((T) temp) > 0; j = j - len) {
							data[j + len] = data[j];
						}
						data[j + len] = temp;
					}
				}
				if (len == 1) {
					break;
				}
			}
		}
		return data;
	}

	/**
	 * 
	 * method: simpleSelectionSort
	 * description: 简单选择排序
	 * date: 2014年10月16日下午10:00:10
	 * auth: ping
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Comparable<T>[] simpleSelectionSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			for (int i = 0; i < data.length; i++) {
				Comparable<T> min = data[i];
				int n = i; //最小数的索引
				for (int j = i + 1; j < data.length; j++) {
					if (data[j].compareTo((T) min) < 0) { //找出最小的数
						min = data[j];
						n = j;
					}
				}
				data[n] = data[i];
				data[i] = min;
			}
		}
		return data;
	}

	/**
	 * 
	 * method: heapSort
	 * description: 堆排序
	 * date: 2014年10月16日下午10:04:07
	 * auth: ping
	 * @param data
	 * @return
	 */
	public static <T> Comparable<T>[] heapSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			int len = data.length;
			//循环建堆  
			for (int i = 0; i < len - 1; i++) {
				//建堆  
				buildMaxHeap(data, len - 1 - i);
				//交换堆顶和最后一个元素  
				swap(data, 0, len - 1 - i);
			}
		}
		return data;
	}

	/**
	 * 
	 * method: bubbleSort
	 * description: 冒泡排序
	 * date: 2014年10月16日下午10:17:59
	 * auth: ping
	 * @param data
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Comparable<T>[] bubbleSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < data.length - i - 1; j++) {
					//这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
					if (data[j].compareTo((T) data[j + 1]) > 0) {
						Comparable<T> temp = data[j];
						data[j] = data[j + 1];
						data[j + 1] = temp;
					}
				}
			}
		}
		return data;
	}

	/**
	 * 
	 * method: quickSort
	 * description: 快速排序
	 * date: 2014年10月16日下午10:20:57
	 * auth: ping
	 * @param data
	 * @return
	 */
	public static <T> Comparable<T>[] quickSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			quick(data, 0, data.length - 1);
		}
		return data;
	}

	/**
	 * 
	 * method: megerSort
	 * description: 归并排序
	 * date: 2014年10月16日下午10:35:44
	 * auth: ping
	 * @param data
	 * @return
	 */
	public static <T> Comparable<T>[] megerSort(Comparable<T>[] data) {
		if (data != null && data.length > 1) {
			mergeOperation(data, 0, data.length - 1);
		}
		return data;
	}

	/**
	 * 
	 * method: baseSort
	 * description: 基数排序，目前只能对整数排序
	 * date: 2014年10月16日下午10:45:23
	 * auth: ping
	 * @param data
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int[] baseSort(int[] data) {
		if (data != null && data.length > 1) {
			//找到最大数，确定要排序几趟
			int max = 0;
			for (int i = 0; i < data.length; i++) {
				if (max < data[i]) {
					max = data[i];
				}
			}
			//判断位数
			int times = 0;
			while (max > 0) {
				max = max / 10;
				times++;
			}
			//建立十个队列
			List<ArrayList> queue = new ArrayList<ArrayList>();
			for (int i = 0; i < 10; i++) {
				ArrayList queue1 = new ArrayList();
				queue.add(queue1);
			}
			//进行times次分配和收集
			for (int i = 0; i < times; i++) {
				//分配
				for (int j = 0; j < data.length; j++) {
					int x = data[j] % (int) Math.pow(10, i + 1)
							/ (int) Math.pow(10, i);
					ArrayList queue2 = queue.get(x);
					queue2.add(data[j]);
					queue.set(x, queue2);
				}
				//收集
				int count = 0;
				for (int j = 0; j < 10; j++) {
					while (queue.get(j).size() > 0) {
						ArrayList<Integer> queue3 = queue.get(j);
						data[count] = queue3.get(0);
						queue3.remove(0);
						count++;
					}
				}
			}
		}
		return data;
	}

	private static <T> void mergeOperation(Comparable<T>[] data, int left,
			int right) {
		if (left < right) {
			int middle = (left + right) / 2;
			//对左边进行递归
			mergeOperation(data, left, middle);
			//对右边进行递归
			mergeOperation(data, middle + 1, right);
			//合并
			merge(data, left, middle, right);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> void merge(Comparable<T>[] data, int left, int middle,
			int right) {
		Comparable<T>[] tmpArr = new Comparable[data.length];
		int mid = middle + 1; //右边的起始位置
		int tmp = left;
		int third = left;
		while (left <= middle && mid <= right) {
			//从两个数组中选取较小的数放入中间数组
			if (data[left].compareTo((T) data[mid]) <= 0) {
				tmpArr[third++] = data[left++];
			} else {
				tmpArr[third++] = data[mid++];
			}
		}
		//将剩余的部分放入中间数组
		while (left <= middle) {
			tmpArr[third++] = data[left++];
		}
		while (mid <= right) {
			tmpArr[third++] = data[mid++];
		}
		//将中间数组复制回原数组
		while (tmp <= right) {
			data[tmp] = tmpArr[tmp++];
		}
	}

	private static <T> void quick(Comparable<T>[] data, int low, int high) {
		if (low < high) { //如果不加这个判断递归会无法退出导致堆栈溢出异常
			int middle = getMiddle(data, low, high);
			quick(data, 0, middle - 1);
			quick(data, middle + 1, high);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> int getMiddle(Comparable<T>[] data, int low, int high) {
		Comparable<T> temp = data[low];//基准元素
		while (low < high) {
			//找到比基准元素小的元素位置
			while (low < high && data[high].compareTo((T) temp) >= 0) {
				high--;
			}
			data[low] = data[high];
			while (low < high && data[low].compareTo((T) temp) <= 0) {
				low++;
			}
			data[high] = data[low];
		}
		data[low] = temp;
		return low;
	}

	/**
	 * 
	 * method: buildMaxHeap
	 * description: 对data数组从0到lastIndex建大顶堆
	 * date: 2014年10月16日下午10:05:32
	 * auth: ping
	 * @param data
	 * @param lastIndex
	 */
	@SuppressWarnings("unchecked")
	public static <T> void buildMaxHeap(Comparable<T>[] data, int lastIndex) {
		//从lastIndex处节点（最后一个节点）的父节点开始 
		for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
			//k保存正在判断的节点 
			int k = i;
			//如果当前k节点的子节点存在  
			while (k * 2 + 1 <= lastIndex) {
				//k节点的左子节点的索引 
				int biggerIndex = 2 * k + 1;
				//如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
				if (biggerIndex < lastIndex) {
					//若果右子节点的值较大  
					if (data[biggerIndex].compareTo((T) data[biggerIndex + 1]) < 0) {
						//biggerIndex总是记录较大子节点的索引  
						biggerIndex++;
					}
				}
				//如果k节点的值小于其较大的子节点的值  
				if (data[k].compareTo((T) data[biggerIndex]) < 0) {
					//交换他们  
					swap(data, k, biggerIndex);
					//将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值  
					k = biggerIndex;
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 
	 * method: swap
	 * description: 数据交换
	 * date: 2014年10月16日下午10:05:48
	 * auth: ping
	 * @param data
	 * @param i
	 * @param j
	 */
	private static <T> void swap(Comparable<T>[] data, int i, int j) {
		Comparable<T> tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	/**
	 * 
	 * method: printToResult
	 * description: 打印结果
	 * date: 2014年10月16日下午9:35:11
	 * auth: ping
	 * @param data
	 */
	public static <T> void printToResult(Comparable<T>[] data) {
		if (data != null && data.length > 0) {
			for (int i = 0; i < data.length; i++) {
				System.out.print(data[i] + ",");
			}
		} else {
			System.out.println("Illegault Data!!!!!!!!");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Random random = new Random();
		int size = 20;
		Integer[] data = new Integer[size];
		int[] baseData = new int[size];
		for (int i = 0; i < size; i++) {
			data[i] = random.nextInt(i + 100);
			baseData[i] = random.nextInt(i + 200);
		}
		SortingHelper.printToResult(data);
		SortingHelper.printToResult(SortingHelper.insertionSort(data));
		SortingHelper.printToResult(SortingHelper.binaryInsertionSort(data));
		SortingHelper.printToResult(SortingHelper.shellSort(data));
		SortingHelper.printToResult(SortingHelper.simpleSelectionSort(data));
		SortingHelper.printToResult(SortingHelper.heapSort(data));
		SortingHelper.printToResult(SortingHelper.bubbleSort(data));
		SortingHelper.printToResult(SortingHelper.quickSort(data));
		SortingHelper.printToResult(SortingHelper.megerSort(data));
		baseData = SortingHelper.baseSort(baseData);
		for (int i = 0; i < baseData.length; i++) {
			System.out.print(baseData[i] + ",");
		}
	}
}
