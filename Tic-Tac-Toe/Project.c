#include <stdio.h>
#include <string.h>
void Initial_Board();
void computer_play();
void start_play();
void Multi_play();
void initial();
void Drawing_Board();
void Game_On();
void Computer_Turn();
void My_Turn();
int middle_sk(char);
int Computr_middle();
int Computer_corner();
int Computer_side();
void Gaming_Rule();
void introduction();
int Decide(char);
int my_turn_check();
void scoreboard();
char name1[20];
char name2[20];
char name3[20];
char b[3][3];
int count1=0,count2=0,draw=0;
char computer, you;
char square[10] = { 'o', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

int checkwin();
void board();
int main(void)
{
    int m=0;
    while(1)
    {
        m++;
        if(m>1)
        getchar();
       // system("cls");
       int test;
     //  printf("\n\n\n     Press 1 to Go Menu--->>>\n");
      // scanf("%d",&test);
       //if(test==1){
            system("cls");
       printf("               MENU              \n");
       printf("             ========\n\n\n");
        printf("Press 1 To Play\n\n");
        printf("Press 2 To Exit The Game\n\n");
        printf("Press 3 To Introduction Of Tic-Tac-Toe\n\n");
        printf("Press 4 To Go to Gaming_Rule\n\n");
        printf("Press 5 To Go To Scoreboard\n\n");
        int option_select;
        scanf("%d",&option_select);
        if(option_select==1)
        {
            start_play();
        square[0]='0';
        square[1]='1';
        square[2]='2';
        square[3]='3';
        square[4]='4';
        square[5]='5';
        square[6]='6';
        square[7]='7';
        square[8]='8';
        square[9]='9';
        }
        if(option_select==3)
        {
            introduction();
        }
        if(option_select==4)
        {
            Gaming_Rule();
        }
        if(option_select==5)
            scoreboard();
        if(option_select==2)
            break;
            getchar();
    }
    //}
    return 0;

}
void start_play()
{
    printf("Press -->> 1 To Play Multiplayer:\n\n\n");
    printf("Press -->> 2 To Play With Computer:\n\n\n");
    int option;
    scanf("%d",&option);
    if(option==1)
    {
        Multi_play();
    }

    if(option==2)
    {
        computer_play();
    }
}
void Multi_play()
{
    int player = 1, i, choice;

    printf("Type 1st Player Name:\n\n");
    scanf("%s",name1);
    printf("Type 2nd Player Name:\n\n");
    scanf("%s",name2);
    char mark,name[20];
    do
    {
        board();
        if(player%2)
        {
            player=1;
            strcpy(name,name1);
        }
        else
        {
            player=2;
            strcpy(name,name2);
        }

        printf(" %s, enter a number: ", name);
        scanf("%d", &choice);

        if(player==1)
            mark='X';
        else
            mark='O';

        if (choice == 1 && square[1] == '1')
            square[1] = mark;

        else if (choice == 2 && square[2] == '2')
            square[2] = mark;

        else if (choice == 3 && square[3] == '3')
            square[3] = mark;

        else if (choice == 4 && square[4] == '4')
            square[4] = mark;

        else if (choice == 5 && square[5] == '5')
            square[5] = mark;

        else if (choice == 6 && square[6] == '6')
            square[6] = mark;

        else if (choice == 7 && square[7] == '7')
            square[7] = mark;

        else if (choice == 8 && square[8] == '8')
            square[8] = mark;

        else if (choice == 9 && square[9] == '9')
            square[9] = mark;

        else
        {
            printf("Invalid move \n");

            player--;
            getch();
        }
        i = checkwin();

        player++;
    }
    while (i ==  - 1);

    board();

    if (i == 1){
      //  printf("==>\aPlayer %d win \n", --player);
        printf("\n%s win the game\n\n",name);
    --player;
    if(player==1)
        count1++;
    else
        count2++;
    }
    else{
        printf("==>\aGame draw\n");
        draw++;
    }

    getch();
}
int checkwin()
{
    if (square[1] == square[2] && square[2] == square[3])
        return 1;

    else if (square[4] == square[5] && square[5] == square[6])
        return 1;

    else if (square[7] == square[8] && square[8] == square[9])
        return 1;

    else if (square[1] == square[4] && square[4] == square[7])
        return 1;

    else if (square[2] == square[5] && square[5] == square[8])
        return 1;

    else if (square[3] == square[6] && square[6] == square[9])
        return 1;

    else if (square[1] == square[5] && square[5] == square[9])
        return 1;

    else if (square[3] == square[5] && square[5] == square[7])
        return 1;

    else if (square[1] != '1' && square[2] != '2' && square[3] != '3' &&
             square[4] != '4' && square[5] != '5' && square[6] != '6' && square[7]
             != '7' && square[8] != '8' && square[9] != '9')

        return 0;
    else
        return  - 1;
}
void board()
{
    system("cls");
    printf("\n\n\tTic Tac Toe\n\n");


    printf("Player 1 : %s    ",name1);
    printf("Player 2 : %s",name2);
    //printf("Player 1 (X)  -  Player 2 (O)\n\n\n");


    printf("     |     |     \n");
    printf("  %c  |  %c  |  %c \n", square[1], square[2], square[3]);

    printf("_____|_____|_____\n");
    printf("     |     |     \n");

    printf("  %c  |  %c  |  %c \n", square[4], square[5], square[6]);

    printf("_____|_____|_____\n");
    printf("     |     |     \n");

    printf("  %c  |  %c  |  %c \n", square[7], square[8], square[9]);

    printf("     |     |     \n\n");
}
void computer_play()
{

    initial();
   // Drawing_Board();
    computer = 'O';
    you = 'X';
    Game_On();
}
void initial()
{
    b[0][0]='1';
    b[0][1]='2';
    b[0][2]='3';
    b[1][0]='4';
    b[1][1]='5';
    b[1][2]='6';
    b[2][0]='7';
    b[2][1]='8';
    b[2][2]='9';
}
void Drawing_Board(void)
{
    system("cls");
    printf("%s  VS Computer\n",name3);
    printf("     |     |     \n");
    printf("  %c  |  %c  |  %c \n", b[0][0], b[0][1], b[0][2]);

    printf("_____|_____|_____\n");
    printf("     |     |     \n");

    printf("  %c  |  %c  |  %c \n", b[1][0], b[1][1], b[1][2]);

    printf("_____|_____|_____\n");
    printf("     |     |     \n");

    printf("  %c  |  %c  |  %c \n", b[2][0], b[2][1], b[2][2]);

    printf("     |     |     \n\n");
    return;
}
void Game_On(void)
{
    printf("Enter The Player Name\n\n");
    scanf("%s",name3);
    Drawing_Board();
    int t;
    for (t = 1; t <= 9; t++)
    {
        if (t % 2 == 1)
            My_Turn();
        else
            Computer_Turn();
        Drawing_Board();
        if (Decide(computer))
        {
            printf("\n %s Lose \n\n",name1);
            return;
        }
        else if (Decide(you))
        {
            printf("\nCongratulations, %s win This Round\n\n",name3);
            return;
        }
    }
    printf("\n Game Draw.\n");
    return;
}
void Computer_Turn(void)
{
    int sq;
    int r, c;
    sq= middle_sk(computer);
    if (!sq)  sq = middle_sk(you);
    if (!sq)  sq = Computr_middle();
    if (!sq)  sq = Computer_corner();
    if (!sq)  sq = Computer_side();
    r = (sq -1) / 3;
    c= (sq - 1) % 3;
    b[r][c] = computer;
    return;
}
int middle_sk(char s)
{
    int sq, r, c;
    int res = 0;
    for (sq = 1; sq<= 9; sq++)
    {
        r = (sq - 1) / 3;
        c = (sq - 1) % 3;
        if (b[r][c]>='1' && b[r][c]<='9')
        {
            char p=b[r][c];
            b[r][c] = s;
            if (Decide(s))
                res = sq;
            b[r][c] =p;
        }
    }
    return res;
}
int Computr_middle(void)
{
    if (b[1][1] == '5')  return 5;
    else  return 0;
}
int Computer_corner(void)
{
    if (b[0][0] == '1')  return 1;
    if (b[0][2] == '3')  return 3;
    if (b[2][0] == '7')  return 7;
    if (b[2][2] == '9')  return 9;
    return 0;
}
int Computer_side(void)
{
    if (b[0][1] == '2')  return 2;
    if (b[1][0] == '4')  return 4;
    if (b[1][2] == '6')  return 6;
    if (b[2][1] == '8')  return 8;
    return 0;
}
int Decide(char s)
{
    int r, c;
    for (r = 0; r < 3; r++)
    {
        if ((b[r][0] == s) && (b[r][1] == s) && (b[r][2] == s))
            return 1;
    }
    for (c = 0; c < 3; c++)
    {
        if ((b[0][c] == s) && (b[1][c] == s) && (b[2][c] == s))	return 1;
    }

    if ((b[0][0] == s) && (b[1][1] == s) && (b[2][2] == s)) return 1;

    if ((b[0][2] == s) && (b[1][1] == s) && (b[2][0] == s))  return 1;

    return 0;
}
void My_Turn(void)
{
    int sq;
    int r, c;
    do
    {
        printf("Enter a block square number(From 1-9) : ");
        scanf("%d", &sq);
    }
    while (!my_turn_check(sq));
    r = (sq - 1) / 3;
    c = (sq- 1) % 3;
    b[r][c] = you;
    return;
}
int my_turn_check(int sq)
{
    int r, c;
    r= (sq - 1) / 3;
    c = (sq - 1) % 3;
    if ((sq >= 1) && (sq <= 9))
    {
        if (b[r][c] >= '1' || b[r][c]<='9')  return 1;
    }
    return 0;
}
void introduction()
{
    system("cls");
    printf("Tic-Tac-Toe is a very simple two player game. So only two players can play at a time. This game is also known as Noughts and Crosses or Xs and Os game. One player plays with X and the other player plays with O. In this game we have a board consisting of a 3X3 grid. The number of grids may be increased.\n\n");
}
void Gaming_Rule()
{
    system("cls");
    printf("1-->>Traditionally the first player plays with X. \n\n 2-->>So you can decide who wants to go X and who wants go with o.\n\n 3-->>Only one player can play at a time.\n\n4-->>If any of the players have filled a square then the other player \n\nand the same player cannot override that square.\n\n");

    printf("5-->>There are only two conditions that may be match will be draw or may be win.\n\n");
    printf("6-->>The player that succeeds in placing three respective mark (X or O) in a horizontal, vertical or diagonal row wins the game.\n\n");

}
void scoreboard(){
    system("cls");
    //if(count1>1)
printf("%s win %d number of round\n\n",name1,count1);
//if(count2>1)
printf("%s win %d number of round\n\n",name2,count2);
printf("%d Number Of Draws\n\n",draw);

}


