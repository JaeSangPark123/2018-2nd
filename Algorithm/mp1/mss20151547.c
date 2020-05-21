#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<time.h>
int start, end;//index of maximum sequence 
int max_i;//if the total elements in array are minus, then store the index of maximum element
int flag = 0;//if the total elements in array are minus, then flag becomes 0 else 1
int  o_n_2(int*, int N);
int o_nlog(int*, int left, int right);
int  o_n(int*, int N);

int main(int argc,char* argv[]) {
	int N;//the total size of Array
	int*A; //Array pointer
	int i;//index for loops
	char input[100];//the name of input file
	char output[100];//the name of output file
	char method;//the method to get max sequence 
	int max;//the maximum of sum of subsequence
	clock_t start_time = 0, end_time = 0;//for test the running time
	FILE*fp;//inputfile pointer
	FILE*fp2;//outputfile poiter
	int error;//to check whether scan successfully
	method = argv[2][0];
	strcpy(input,argv[1]);//input file name
	sprintf(output,"result_%s",input);//output file name
	fp = fopen(input,"r");//inputfile pointer
	fp2 = fopen(output,"w");//outputfile pointer
	error = fscanf(fp,"%d",&N);
	A = (int*)malloc(sizeof(int)*N);
	srand(time(NULL));
	for (i = 0; i < N; i++) {
		error = fscanf(fp," %d",&A[i]);
	}
	start_time = clock();
	if(method == '1'){
		printf("method 1 \n");
		max = o_n_2(A,N);
	}
	else if(method =='2'){
		printf("method 2\n");
		max = o_nlog(A,0,N-1);
	}
	else if(method =='3'){
		printf("method 3\n");
		max = o_n(A,N);
	}
	else{
		printf("No method\n");
		return 0;
	}
	end_time = clock();
	error =	fprintf(fp2,"%s\n%c\n%d\n%d\n%d\n%d\n",input,method,N,start,end,max);//outputfile writing
	error = fprintf(fp2,"%lf\n",(double)(end_time-start_time)/1000);//print running time
	free(A);
	fclose(fp);
	fclose(fp2);
	return 0;
}
int  o_n_2(int* A, int N) {
	int currentSum;
	int maximum=A[0];//maximum value in the array
	int maxSum, i, j;
	maxSum = A[0];
	for (i = 0; i < N; i++) {
		currentSum = A[i];
		if (A[i] > 0) {
			flag = 1;
		}
		if (maximum < A[i]) {
			maximum = A[i];
			max_i = i;
		}
		for (j = i + 1; j < N; j++) {
			currentSum += A[j];
			if (currentSum > maxSum) {//updating the maximum sum
				maxSum = currentSum;
				start = i;
				end = j;
			}
		}
	}
	if (flag== 0) {//if the 
		start = end = max_i;
		return A[max_i];//return maximu element of array
	}
	return maxSum;
}
int o_nlog(int*A, int left, int right) {
	int maxLeftsum, maxRightsum;//the maximum of subsequeuce sum respectively from leftside and rightside of A
	int leftSideSum =0, rightSideSum = 0;//the subsequence sum from the center to left and right respectively
	int maxLeftSide=-999999, maxRightSide=-999999;//the max subsequence sum from the center to left and right respectively
	int center, i;
	int maximum=A[left];//maximum value
	int i_left, i_right;//the start and end index of subsequence sum
	if (left == right) {//if the array size be 1
		return A[left];//return that element
	}
	center = (left + right) / 2;
	maxLeftsum = o_nlog(A, left, center);
	maxRightsum = o_nlog(A, center + 1, right);
	for (i = center; i >= left; i--) {
		leftSideSum += A[i];
		if (A[i] >= 0) {//check if all of elements in array is minus
			flag = 1;
		}
		if (maximum < A[i]) {
			maximum = A[i];
			max_i = i;
		}
		if (leftSideSum > maxLeftSide) {
			maxLeftSide = leftSideSum;
			i_left = i;
		}
	}
	for (i = center + 1; i <= right; i++) {
		rightSideSum += A[i];
		if (A[i] >= 0) {//check if all of elements in array is minus
			flag = 1;
		}
		if (maximum < A[i]) {
			maximum = A[i];
			max_i = i;
		}
		if (rightSideSum > maxRightSide) {
			maxRightSide = rightSideSum;
			i_right = i;
		}
	}
	if (flag == 1) {
		if (maxLeftsum >= maxRightsum) {
			if (maxLeftsum >= (maxRightSide + maxLeftSide)) {//if the sum of subsequence of left side is biggest
				
				return maxLeftsum;
			}
			else {//if the sum of subsequence from center is biggest
				start = i_left;
				end = i_right;
				return maxRightSide + maxLeftSide;
			}
		}
		else if (maxLeftsum < maxRightsum) {
			if (maxRightsum >= (maxRightSide + maxLeftSide)) {//if the sum of subsequence of right side is biggest
				return maxRightsum;
			}
			else {
				start = i_left;
				end = i_right;
				return maxRightSide + maxLeftSide; //if the sum of subsequence from center is biggest
			}
		}
	}
	else {
		start = end = max_i;
		return A[max_i];
	}

}
int  o_n(int*A, int N){

	int currentSum=0, maxSum=0, i;//current : current sum of subsequence 
							  //maxSum: max value in currentSum,
							  // i :for loops index
	int maximum=A[0];//the max value in currentSum
	int i_left=0;//temporary variable of left and right index of subsequence 
	for (i = 0; i < N; i++) {
		if (A[i] > 0) {
			flag = 1;
		}
		if (maximum < A[i]) {//updating the max value in array
			maximum = A[i];
			max_i = i;
		}
		currentSum += A[i];
		if (currentSum > maxSum) {//updating the max sum
			maxSum = currentSum;
			end = i;
			start = i_left; //updating the left and right index of subsequence
		}
		else if (currentSum < 0) {//if possible, start from next element
			currentSum = 0;
			if (i < N - 1) {
				i_left = i + 1;
			}
		}
	}
	if (flag == 0) {//if all of elements be minus
		start = end = max_i;
		return A[max_i];//return max value
	}
	else {
		return maxSum;
	}
}
