#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<time.h>

int start, end;//index of maximum sequence 
int max_i;//if the total elements in array are minus, then store the index of maximum element
int flag = 0;//if the total elements in array are minus, then flag becomes 0 else 1

void bubblesort(int*,int);
void quicksort(int*,int, int);
int partition(int*,int,int);
void mergesort(int*,int,int);
void merge(int*,int,int,int);
void mysort(int*);

void swap(int*A,int i,int k){
	int temp=0;
	temp =A[i];
	A[i]=A[k];
	A[k] = temp;
}
long int N;//the total size of Array
int main(int argc,char* argv[]) {
	int*A; //Array pointer
	int i;//index for loops
	char input[100];//the name of input file
	char output[100];//the name of output file
	char method;//the method to get max sequence 
	clock_t start_time = 0, end_time = 0;//for test the running time
	FILE*fp;//inputfile pointer
	FILE*fp2;//outputfile poiter
	int error;//to check whether scan successfully
	method = argv[2][0];
	strcpy(input,argv[1]);//input file name
	sprintf(output,"result_%c_%s",method,input);//output file name
	fp = fopen(input,"r");//inputfile pointer
	fp2 = fopen(output,"w");//outputfile pointer
	error = fscanf(fp,"%lu",&N);
	A = (int*)malloc(sizeof(int)*N);
	srand(time(NULL));
	for (i = 0; i < N; i++) {
		error = fscanf(fp," %d",&A[i]);
	}
	start_time = clock();
	if(method == '1'){
		printf("method 1");
		bubblesort(A,N);
	}
	else if(method =='2'){
		printf("method 2");
		quicksort(A,0,N-1);
	}
	else if(method =='3'){
		printf("method 3");
		mergesort(A,0,N-1);
	}
	else if(method =='4'){
		printf("method 4");
		mysort(A);
	}	
	else{
		printf("No method");
		return 0;
	}
	end_time = clock();
	error =	fprintf(fp2,"%s\n%c\n%lu\n",input,method,N);//outputfile writing
	error = fprintf(fp2,"%lf\n",(double)(end_time-start_time)/1000);//print running time
	for(i=0;i<N;i++){
		error = fprintf(fp2,"%d ",A[i]);
	}
	printf("\n");
	free(A);
	fclose(fp);
	fclose(fp2);
	return 0;
}
void bubblesort(int *A,int N){
	int i=0;
	int k=0;
	for(i=0;i<N;i++){
		for(k=0;k<N-1;k++){
			if(A[k]>A[k+1]){
				swap(A,k,k+1);
			}
		}
	}
}
void quicksort(int*A,int left,int right){
	int pivot;
	if(right-left>0){
		pivot=partition(A,left,right);
	quicksort(A,left,pivot-1);
	quicksort(A,pivot+1,right);
	}
}
int partition(int*A,int left,int right){
	int i,pivot;
	pivot = left;
	for(i=left;i<right;i++){
		if(A[i]<A[right]){
		swap(A,i,pivot);
		pivot++;
		}
	}
	swap(A,right,pivot);
	return pivot;
}
void mergesort(int*A,int left,int right){
	if(left<right){
		mergesort(A,left,(left+right)/2);
		mergesort(A,(left+right)/2+1,right);
		merge(A,left,(left+right)/2,right);
	}
}
void merge(int* A,int left,int middle,int right){
	int* B;
	int i,j,k;
	B = (int*)malloc(sizeof(int)*N);
	for(i=0;i<N;i++){
		B[i] = A[i];
	}
	j=left; k = middle+1;
	i = left;
	while(1){
		if(j>middle||k>right){
			break;
		}
		if(B[j]<B[k]){
			A[i]=B[j];
			j++;
		}
		else{
			A[i]=B[k];
			k++;
		}
		i++;
	}
	for(j;j<=middle;j++){ //좌측에 남아있는 값 순차적으로 대입
		A[i] = B[j];
		i++;
	}
	for(k;k<=right;k++){//우측에 남아있는 값 대입
		A[i] = B[k];
		i++;
	}
}
void mysort(int*A){
	int n=0;
	int i=0;
	if(N<=1000000){
		quicksort(A,0,N-1);
	}
	else{
		for(i=0;i<N-1;i++){
			if(A[i]>A[i+1]){
				n++;
			}
		}
		if(n>(N/2)){
			for(i=0;i<N/2;i++){
				swap(A,i,N-i);
			}
		}
		quicksort(A,0,N-1);
	}
}
