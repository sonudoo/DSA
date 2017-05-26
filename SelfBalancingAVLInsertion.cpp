#include <bits/stdc++.h>
using namespace std;
struct tree{
	int data;
	struct tree *left;
	struct tree *right;
	int lh;	//Left subtree height
	int rh; //Right subtree height
	int bf; //Balance factor (Left subtree height - Right subtree Height)
};
typedef struct tree *node;
node getNode(){
	node n = (node)malloc(sizeof(struct tree));
	n->left = NULL;
	n->right = NULL;
	n->data = -1;
	n->lh = 0;
	n->rh = 0;
	n->bf = 0;
	return n;
}
bool search(node curr,int key){
	if(curr==NULL)	return false;
	if(curr->data==key)	return true;
	if(curr->data>key)	return search(curr->left,key);
	else	return search(curr->right,key);
}
node RightRotate(node curr){
	printf("RightRotate at %d\n",curr->data);
	int temp1 = curr->rh;
	int temp2 = (curr->left)->rh;
	curr->lh = temp2;
	(curr->left)->rh = temp1+1;
	curr->bf = curr->lh - curr->rh;
	(curr->left)->bf = (curr->left)->lh - (curr->left)->rh;
	node temp = (curr->left)->right;
	node tem = (curr->left);
	(curr->left)->right = curr;
	curr->left = temp;
	printf("Right rotation successful. New curr = %d\n",tem->data);
	return tem;
}
node LeftRotate(node curr){
	printf("LeftRotate at %d\n",curr->data);
	int temp1 = curr->lh;
	int temp2 = (curr->right)->lh;
	curr->rh = temp2;
	(curr->right)->lh = temp1+1;
	curr->bf = curr->lh - curr->rh;
	(curr->right)->bf = (curr->right)->lh - (curr->right)->rh;
	node temp = (curr->right)->left;
	node tem = (curr->right);
	(curr->right)->left = curr;
	curr->right = temp;
	printf("Left rotation successful. New curr = %d\n",tem->data);
	return tem;
}
node insert(node curr,int key){
	if(curr==NULL){
		curr = getNode();
		curr->data = key;
		return curr;
	}
	if(key<curr->data){
		curr->left = insert(curr->left,key);
		curr->lh = max((curr->left)->lh,(curr->left)->rh)+1;
	}
	else{
		curr->right = insert(curr->right,key);
		curr->rh = max((curr->right)->lh,(curr->right)->rh)+1;
	}
	curr->bf = curr->lh - curr->rh;
	if(curr->bf>=2 || curr->bf<=-2){
		if(curr->bf==2 && (curr->left)->bf==1){
			curr = RightRotate(curr);
		}
		else if(curr->bf==2 && (curr->left)->bf==-1){
			curr->left = LeftRotate(curr->left);
			curr = RightRotate(curr);
		}
		else if(curr->bf==-2 && (curr->right)->bf==-1){
			curr = LeftRotate(curr);
		}
		else{
			curr->right = RightRotate(curr->right);
			curr = LeftRotate(curr);
		}
	}
	return curr;
}
int main(){
	//freopen("stdin.txt","r",stdin);
	int n,key;
	scanf("%d",&n);
	node root = getNode();
	scanf("%d",&key);
	root->data = key;
	for(int i=1;i<n;i++){
		scanf("%d",&key);
		if(!search(root,key)){
			root = insert(root,key);
			printf("Key %d inserted successfully\n",key);
		}
		else{
			printf("Key %d already exists\n",key);
		}
	}
	return 0;
}
