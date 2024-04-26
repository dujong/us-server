# ☁ 클라우드 아키텍처를 만들면서 했던 고민들

## ⚡ VPC를 어떻게 설계해야 할까?

### 고려 사항
1. Develop/Production 구분해야 한다.
2. 규모는 어느정도로 해야할까?

### 해결 방안
1. VPC 대역 구분
   - Develop: 198.168.0.0/24
   - Production: 10.0.0.0/24
2. 서브넷 분리, 현재 사용 중인 컨테이너가 2개인 점을 고려했을 때, 현재 프로젝트 수준에서 아무리 확장성을 염두해 둔다고 해도 250개 이상의 호스트가 필요할 것 같지는 않다.
    - CIDR 블록은 /24

## ⚡ Subnet 구성을 어떻게 할까?

### 고려 사항
1. ECS 클러스터를 위해 최소 두개의 VPC 서브넷이 필요하다.
2. DB, WAS는 프라이빗 서브넷에 위치시켜야 한다.

### 해결 방안
- 퍼블릭 서브넷1, 프라이빗 서브넷2개로 운영한다.
  - Public Subnet
    - NAT 게이트웨이, ELB를 위치시킨다.
  - Private Subnet
    - DB 서버용 서브넷,  WAS 용 서브넷을 구분한다.

## ⚡ AZ는 몇개로 해야할까?
- AZ는 많이 사용할수록 좋겠지만, 프로젝트가 지원금이 나오는 것도 아니니 최대한 절약해야 한다.
- EKS를 사용할 필요 없으므로 2개 이하로 선택이 가능하다.
- **Develop VPC**
  - 고가용성이 필요 없으므로 1개만 사용한다.
- **Production VPC**
  - 고가용성 확보를 위해 2개를 사용한다.
  - 어느 정도 수준의 가용성을 맞출 것인가?
    >AWS 안정성 원칙에 따르면 현재 진행하는 프로젝트는 99.95%의 가용성이 필요하다. 하지만 가용성 때문에 제품 출시를 미룰 수는 없기 때문에 99%를 목표로 진행한다.

## 🪐 어떤 AWS 서비스를 선택할까?

### 필요한 기능
1. Computing
   - **ECS**
     - 도커 허브를 사용했을 때 프라이빗 이미지는 1개 밖에 지정하지 못해서 계정을 두개 사용해야 하는 불편함이 있다.
     - Container가 다운되었을 때, 알림을 받고 재실행 하는 과정을 ECS Service에서 스크립트화가 가능하다.
2. DB
   - **RDS MariaDB**
     - MariaDB에서 JSON_EXIST 함수를 지원한다.
     - AWS RDS에서 MySQL과 비용차이는 나지 않지만 MariaDB가 쿼리 실행 속도가 더 빠르다.
3. Storage
   - **S3**
     - 썸네일 이미지, 프로필 이미지, 로그 저장 용도로 활용한다.
4. Cache
   - **ElastiCache**
     - Redis Cache 용도로 활용한다.

### 부가 기능
- Lambda
  - 썸네일 생성 용도로 활용한다.(보류)

## 🚀 어떻게 배포할까?
이건 고려사항이 없어서 익숙한 **GitHub Actions**를 사용한다.
