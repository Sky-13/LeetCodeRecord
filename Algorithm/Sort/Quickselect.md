# 1.介绍
```plain
Quickselect 是一种从无序列表中选择第 k 小元素的选择算法。它是快速排序（Quicksort）的一种变体，
但不像快速排序那样对整个数组进行排序，它只对部分数据进行操作，以找到所需的第 k 小元素。因此，在平均
情况下，它的效率比完整排序要高，时间复杂度为 O(n)，其中 n 是列表中的元素数量。然而，在最坏的情况下，
如果每次分割都极不均衡，则时间复杂度可能退化到 O(n²)。
```

# 2.实现
## 基于简单交换排序
基本思想：

<font style="color:rgb(77, 77, 77);">利用简单交换排序的特点，即</font>**<font style="color:rgb(77, 77, 77);">每完成一趟排序，就至少会有一个元素的位置确定</font>**<font style="color:rgb(77, 77, 77);">，那么只需要对数组 nums 进行 k 躺降序排序，此时的 nums[k - 1] 必为数组 nums 中第 k 个最大的元素，将其返回即可。该算法的时间复杂度为 </font>`<font style="color:rgb(199, 37, 78);background-color:rgb(249, 242, 244);">O(k * n)</font>`<font style="color:rgb(199, 37, 78);background-color:rgb(249, 242, 244);">。</font>

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
    	//进行 k 趟排序
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] < nums[j]) {
                	//交换 nums[i] 和 nums[j]
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        return nums[k - 1];
    }
}
```

## 基于堆排序
基本思想：

<font style="color:rgb(77, 77, 77);">使用</font>**<font style="color:rgb(77, 77, 77);">堆排序</font>**<font style="color:rgb(77, 77, 77);">来找出数组中的第 k 个最大元素，即</font>**<font style="color:rgb(77, 77, 77);">建立一个大根堆，做 k − 1 次删除操作后堆顶元素就是我们要找的答案</font>**<font style="color:rgb(77, 77, 77);">。在 Java 中，我们可以直接使用</font>**<font style="color:rgb(77, 77, 77);">优先级队列</font>**<font style="color:rgb(77, 77, 77);">来完成这一操作。当然，如果想更加了解推排序，我们也可以手动实现。该算法的时间复杂度为 </font>`<font style="color:rgb(199, 37, 78);background-color:rgb(249, 242, 244);">O(nlogk)。</font>`

```java

```

## 基于快速排序
基本思想：

1. <font style="color:rgb(44, 44, 54);">选择一个“基准”元素（pivot），这通常是从列表中随机选择的一个元素。</font>
2. <font style="color:rgb(44, 44, 54);">对数组进行分区操作，使得所有小于基准的元素都在基准的左侧，所有大于基准的元素都在其右侧。</font>
3. <font style="color:rgb(44, 44, 54);">检查基准的位置：</font>
    - <font style="color:rgb(44, 44, 54);">如果基准正好位于第 k 个位置，则找到了答案。</font>
    - <font style="color:rgb(44, 44, 54);">如果基准的位置在第 k 个位置之前，则在右半部分继续搜索。</font>
    - <font style="color:rgb(44, 44, 54);">如果基准的位置在第 k 个位置之后，则在左半部分继续搜索。</font>

```java
public int quickSort (int[] nums, int l, int r, int k) {
    if (l == r) {
        return nums[k];
    }
    
    int p = l, i = l - 1, j = r + 1;
    while (i < j) {
        do {i++} while (nums[i] < nums[p]);
        do {j--} while (nums[j] > nums[p]);
        if (i < j) {
            swap(nums, i, j);
        }
    }
    if (j <= k) {
        return quickSort(nums, l, j, k);
    } else {
        return quickSort(nums, l, i - 1, k);
    }
}

public void swap (int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

---

# 3.应用
