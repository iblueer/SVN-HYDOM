//
//  JVproductOrderInfoView.m
//  HD_Car
//
//  Created by xingso on 15/8/7.
//  Copyright (c) 2015年 HD_CyYihan. All rights reserved.
//

#import "JVproductOrderInfoView.h"
@interface JVproductOrderInfoView()
@property (weak, nonatomic) IBOutlet UIView *line1;
@property (weak, nonatomic) IBOutlet UIView *line2;
@property (weak, nonatomic) IBOutlet UIView *line3;
@property (weak, nonatomic) IBOutlet UIView *line4;
@property (weak, nonatomic) IBOutlet UIView *line5;
@property (weak, nonatomic) IBOutlet UIView *line6;
@property (weak, nonatomic) IBOutlet UIView *line7;

@property (weak, nonatomic) IBOutlet UIView *payTapView1;
@property (weak, nonatomic) IBOutlet UIView *payTapView2;
@property (weak, nonatomic) IBOutlet UIView *payTapView3;
@property (weak, nonatomic) IBOutlet UIView *payTapView4;
@property (weak, nonatomic) IBOutlet UIView *payTapView5;


@property(weak,nonatomic)UIImageView* payPointer;

@end
@implementation JVproductOrderInfoView
/**添加虚线*/
-(void)addline:(UIView*)view{
    UIImage* backgroundImage=[UIImage imageNamed:@"HDxuxian"];
    UIColor *backgroundColor = [UIColor colorWithPatternImage:backgroundImage];
    view.backgroundColor=backgroundColor;
}


/**添加切换手势*/
-(void)addTapforChange{
    [self.payTapView1 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView1.tag=21;
    [self.payTapView2 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView2.tag=22;
    [self.payTapView3 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView3.tag=23;
    [self.payTapView4 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView4.tag=24;
    [self.payTapView5 addTapGestureRecognizerWithTarget:self action:@selector(changePay:)];
    self.payTapView5.tag=25;
}


-(void)changePay:(UIGestureRecognizer* )gez{
    UIView* view=gez.view;
    UIImageView* imageV=(UIImageView*)view.subviews[0];
    if (self.payPointer!=nil) {
        self.payPointer.image=[UIImage imageNamed:@"redSingle2"];
    }
    imageV.image=[UIImage imageNamed:@"redSingle1"];
    self.payPointer=imageV;
    switch (view.tag) {
        case 21:
            self.payStyle=@"2";
            self.payStyleDescription=@"支付宝";
            break;
        case 22:
            self.payStyle=@"4";
            self.payStyleDescription=@"微信支付";
            break;
        case 25:
            self.payStyle=@"3";
            self.payStyleDescription=@"银联支付";
            break;
        case 23:
            self.payStyle=@"1";
            self.payStyleDescription=@"会员卡支付";
            break;
        default:
            self.payStyle=@"5";
            self.payStyleDescription=@"现场pos机刷卡";
            break;
    }
}

-(void)awakeFromNib{
    // 初始化代码
    [self addline:self.line1];
    [self addline:self.line2];
    [self addline:self.line3];
    [self addline:self.line4];
    [self addline:self.line5];
    [self addline:self.line6];
    [self addline:self.line7];
    self.payStyle=@"2";
    self.payStyleDescription=@"支付宝";
    self.washStyle=@"1";
    self.washStyleDescription=@"清洗车身";
    self.payPointer=(UIImageView*)self.payTapView1.subviews[0];
    
    [self addTapforChange];
}

@end
