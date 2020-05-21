#include<stdio.h>

#include<stdlib.h>
#include<string.h>
#include<time.h>
/*
treepointer newnode,second;
second = (treepointer)malloc(sizeof(treepointer));
newnode->num = 100;
newnode->right = second;
   */
int expo(int n){
	int s = 1;
	for(int i=0;i<n;i++){
		s *=2;
	}
	return s;
}
void reverse(FILE* output,int input){
	int i=0;
	unsigned char str[8]={'0','0','0','0','0','0','0','0'};
	for(i=7;i>=0;i--){
		if(input==0){
			break;
		}
		if(input>=expo(i)){
			str[7-i]= '1';
			input-=expo(i);
		}
	}
	fprintf(output,"%s",str);
}
void generate (FILE*output,char input[]){
	int i=0;
	int temp=0;
	unsigned char ch[256];
	for(i=0;i<256;i++){
		ch[i] = i;
	}
	for(i=7;i>=0;i--){
		if(input[7-i]=='1'){
			temp+=expo(i);
		}
	}
	fprintf(output,"%c",ch[temp]);
}
char T[26][7]={"10","110","010","1110","0110","0010","11110","01110","00110","00010","111110","011110","001110","000110","000010","1111110","0111110","0111111","0011110","0011111","0001110","0001111","0000110","0000111","0000010","0000011"};
void compress(FILE* fp1,FILE* fp2);
void decompress(FILE* fp1,FILE* fp2);
void sort(long long int [],char []);

int main(int argc,char* argv[]) {
	int*A; //Array pointer
	int i;//index for loops
	char method;//the method to get max sequence 
	char input[100];
	char output[100];
	FILE*fp;//inputfile pointer
	FILE*fp2;//outputfile poiter
	int error;//to check whether scan successfully
	method = argv[1][1];
	strcpy(input,argv[2]);//input file name
	if(method == 'c'){		
		sprintf(output,"%s.zz",input);//output file name
		fp = fopen(input,"r+");//outputfile pointer
		fp2 = fopen(output,"w+");//outputfile pointer
		compress(fp,fp2);
	}
	else if(method == 'd'){
		sprintf(output,"%s.yy",input);//output file name
		fp = fopen(input,"r+");//outputfile pointer
		fp2 = fopen(output,"w+");//outputfile pointer
		printf("decompress\n");
		decompress(fp,fp2);
		printf("complete\n");
	}
	else{
		printf("invalid value \n");
		return 0;
	}
	printf("\n");
	fclose(fp);
	fclose(fp2);
	return 0;
}
void compress(FILE* fp1,FILE* fp2){
	FILE* temp;
	int error;
	int i=0;
	int j=0;
	unsigned char c;
	long long int size=0;
	int temp_size=0;
	long long int gaesu[256]= {0,};
	unsigned char charname[256];
	unsigned char chtemp[8];
	temp = fopen("temp","w+");
	fseek(fp1,0,SEEK_END);
	size = ftell(fp1);
	fseek(fp1,0,SEEK_SET);
	for(i=0;i<256;i++){
		charname[i] = i;
	}

	for(i=0;i<size;i++){
		error = fscanf(fp1,"%c",&c);
		gaesu[c]++;
	}
	sort(gaesu,charname);
	fprintf(fp2,"%d\n",(size)%8);
	fseek(fp1,0,SEEK_SET);
	for(i=0;i<256;i++){
		fprintf(fp2,"%c",charname[i]);
	}  // huffman order store
	for(i=0;i<size;i++){
		fscanf(fp1,"%c",&c);
		for(j=0;j<256;j++){
			if(charname[j]==c){
				if(j>=0&&j<26){
					fprintf(temp,"%s",T[j]);
				}
				else{
					for(int k=0;k<j-18;k++){
						fprintf(temp,"1");
					}
					fprintf(temp,"0");
				}
			}
		}
	}
	fseek(temp,0,SEEK_END);
	temp_size = ftell(temp);
	fseek(temp,0,SEEK_SET);
	for(i=0;i<temp_size/8;i++){
		for(int j=0;j<8;j++){
			fscanf(temp,"%c",&chtemp[j]);
		}	
		generate(fp2,chtemp);
	}
	for(i=0;i<size%8;i++){
		fscanf(temp,"%c",&chtemp[i]);
	}
	for(i=0;i<size%8;i++){
		fprintf(fp2,"%c",chtemp[i]);
	}
	fclose(temp);
			
}
void sort(long long int gaesu[],char charname[]){
	int i=0;
	int j=0;
	long long int temp=0;
	char temp1;
	for(j=0;j<255;j++){
		for(i=j;i<256;i++){
			if(gaesu[j]<gaesu[i]){
				temp = gaesu[i];
				gaesu[i] = gaesu[j];
				gaesu[j] = temp;
				temp1 = charname[i];
				charname[i] = charname[j];
				charname[j] = temp1;
			}
		}
	}
}
void decompress(FILE*fp1,FILE*fp2){
	//0~255값은 원래 파일의 frequency
	//읽어온 파일을 01string 으로 잠시 바꾸고 
	//01string에서 원래값으로 복원
	FILE*fp3;
	unsigned char charname[256]={'0'};
	int i=0;
	int surplus=0;
	unsigned char sul[8];
	unsigned char temp=0;
	int j=0;
	unsigned char c=0;
	long long int size=0;
	long long int temp_size=0;
	fp3 = fopen("temp2","w+");
	fseek(fp1,0,SEEK_END);
	size = 	ftell(fp1);
	fseek(fp1,0,SEEK_SET);
	fscanf(fp1,"%d\n",&surplus);
	for(i=0;i<256;i++){
		fscanf(fp1,"%c",&charname[i]);
	}
	for(i;i<size-surplus;i++){
		fscanf(fp1,"%c",&temp);
		reverse(fp3,temp);
	}
	for(i=0;i<surplus;i++){
		fscanf(fp1,"%c",&sul[i]);
	}
	for(i=0;i<surplus;i++){
		fprintf(fp3,"%c",sul[i]);
	}
	fseek(fp3,0,SEEK_END);
	temp_size =ftell(fp1);
	fseek(fp3,0,SEEK_SET);
	for(i=0;ftell(fp3)!=temp_size;i++){
		printf("doing something\n");
		fscanf(fp3,"%c",&c);
		if(c == '1'){//1
			fscanf(fp3,"%c",&c);
			if(c == '0')fprintf(fp2,"%c",charname[0]);//10
			else{//11
				fscanf(fp3,"%c",&c);
				if(c=='0')fprintf(fp2,"%c",charname[1]);//110
				else{//111
					fscanf(fp3,"%c",&c);
					if(c=='0')fprintf(fp2,"%c",charname[3]);	//1110
					else{//1111
						fscanf(fp3,"%c",&c);
						if(c=='0')fprintf(fp2,"%c",charname[6]);//11110
						else{//11111
							fscanf(fp3,"%c",&c);
							if(c=='0')fprintf(fp2,"%c",charname[10]);//111110
							else{//111111
								fscanf(fp3,"%c",&c);
								if(c =='0')fprintf(fp2,"%c",charname[15]);//1111110
								else{//1111111
									while(1){
										fscanf(fp3,"%c",&c);
										if(c=='0'){
											fprintf(fp2,"%c",charname[26+j]);
											break;
										}
										j++;
									}
								}
							}
						}	
					}
				}
			}
		}else if(c=='0'){
			fscanf(fp3,"%c",&c);
			if(c=='1'){//01
				fscanf(fp3,"%c",&c);
				if(c=='0')fprintf(fp2,"%c",charname[2]);//010
				else{
					fscanf(fp3,"%c",&c);
					if(c=='0')fprintf(fp2,"%c",charname[4]);//0110
					else{
						fscanf(fp3,"%c",&c);
						if(c=='0')fprintf(fp2,"%c",charname[7]);//01110
						else{
							fscanf(fp3,"%c",&c);
							if(c=='0')fprintf(fp2,"%c",charname[11]);//011110
							else{
								fscanf(fp3,"%c",&c);
								if(c=='0')fprintf(fp2,"%c",charname[16]);//0111110
								else{
									fprintf(fp2,"%c",charname[17]);
								}
							}
						}
					}
				}
			}
			else{//00
				fscanf(fp3,"%c",&c);
				if(c=='1'){//001
					fscanf(fp3,"%c",&c);
					if(c=='0')fprintf(fp2,"%c",charname[5]);//0010
					else{//0011
						fscanf(fp3,"%c",&c);
						if(c=='0')fprintf(fp2,"%c",charname[8]);//00110
						else{//00111
							fscanf(fp3,"%c",&c);
							if(c=='0')fprintf(fp2,"%c",charname[12]);//001110
							else{//001111
								fscanf(fp3,"%c",&c);
								if(c=='0')fprintf(fp2,"%c",charname[18]);//0011110
								else fprintf(fp2,"%c",charname[19]);//0011111
							}
						}
					}
				}
				else{//000
					fscanf(fp3,"%c",&c);
					if(c=='1'){//0001
						fscanf(fp3,"%c",&c);
						if(c=='0')fprintf(fp2,"%c",charname[9]);//00010
						else{//00011
							fscanf(fp3,"%c",&c);
							if(c=='0')fprintf(fp2,"%c",charname[13]);//000110
							else{//000111
								fscanf(fp3,"%c",&c);
								if(c=='0')fprintf(fp2,"%c",charname[20]);//0001110
								else{
									fprintf(fp2,"%c",charname[21]);//0001111
								}
							}
						}
					}
					else{//0000
						fscanf(fp3,"%c",&c);
						if(c==1){//00001
							fscanf(fp3,"%c",&c);
							if(c=='0')fprintf(fp2,"%c",charname[14]);//000010
							else{//000011
								fscanf(fp3,"%c",&c);
								if(c=='0')fprintf(fp2,"%c",charname[22]);//0000110
								else{
									fprintf(fp2,"%c",charname[23]);//0000111
								}
							}
						}
						else{//00000
							fscanf(fp3,"%c",&c);
							if(c=='1'){//000001
								fscanf(fp3,"%c",&c);
								if(c=='0')fprintf(fp1,"%c",charname[24]);//0000010
								else fprintf(fp1,"%c",charname[25]);//0000011
							}
						}
					}
				}
			}
		}
	}
}

