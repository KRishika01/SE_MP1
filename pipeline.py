# # import os
# # from git import Repo
# # from github import Github
# # from mistralai import Mistral
# # from dotenv import load_dotenv

# # load_dotenv()

# # # ---------------- CONFIG ----------------
# # GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
# # MISTRAL_API_KEY = os.getenv("MISTRAL_API_KEY")
# # REPO_URL = os.getenv("REPO_URL")
# # REPO_NAME = os.getenv("REPO_NAME")
# # BASE_BRANCH = os.getenv("BASE_BRANCH")

# # LOCAL_PATH = "repo"
# # NEW_BRANCH = "mistral-refactor"

# # # ---------------- MISTRAL ----------------
# # mistral = Mistral(api_key=MISTRAL_API_KEY)

# # def analyze_code(code):
# #     prompt = f"""
# # You are a software design expert.

# # Tasks:
# # 1. Identify design smells.
# # 2. Refactor the code.
# # 3. Preserve functionality.

# # STRICT RULES:
# # - Return ONLY valid JSON.
# # - Do NOT use markdown.
# # - Do NOT add explanations.
# # - refactored_code must contain only plain code.

# # Format:

# # {{
# # "smells": ["smell1", "smell2"],
# # "refactored_code": "code here"
# # }}

# # Code:
# # {code}
# # """


# #     response = mistral.chat.complete(
# #         model="mistral-small",
# #         messages=[{"role": "user", "content": prompt}],
# #         temperature=0
# #     )

# #     return response.choices[0].message.content



# # # ---------------- CLONE + BRANCH ----------------
# # # def clone_repo():
# # #     if not os.path.exists(LOCAL_PATH):
# # #         print("Cloning repository...")
# # #         Repo.clone_from(REPO_URL, LOCAL_PATH)

# # #     repo = Repo(LOCAL_PATH)
# # #     git = repo.git

# # #     # Create new branch
# # #     try:
# # #         repo.git.checkout("-b", NEW_BRANCH)
# # #     except:
# # #         repo.git.checkout(NEW_BRANCH)

# # #     return repo
# # def clone_repo():
# #     print("Using repository checked out by GitHub Actions...")
# #     return Repo(".")


# # import json
# # import re

# # def parse_mistral_response(text):
# #     # Remove markdown ```json ``` wrappers
# #     text = text.strip()

# #     # Extract JSON block if wrapped
# #     match = re.search(r"\{.*\}", text, re.DOTALL)
# #     if not match:
# #         raise ValueError("No JSON found")

# #     json_text = match.group(0)

# #     data = json.loads(json_text)

# #     # Clean refactored code (remove ```java ``` if present)
# #     code = data.get("refactored_code", "")

# #     code = re.sub(r"```java", "", code)
# #     code = re.sub(r"```", "", code)
# #     code = code.strip()

# #     data["refactored_code"] = code

# #     return data

# # # ---------------- PROCESS FILES ----------------
# # import json

# # def process_files():
# #     updated_files = []
# #     smell_report = {}
# #     MAXFILES = 3
# #     count = 0
# #     for root, dirs, files in os.walk(LOCAL_PATH):
# #         for file in files:
# #             if file.endswith(".java"):
# #                 path = os.path.join(root, file)

# #                 with open(path, "r", encoding="utf-8") as f:
# #                     code = f.read()

# #                 if len(code) < 50:
# #                     continue

# #                 print("Analyzing:", path)

# #                 try:
# #                     result = analyze_code(code)
# #                     print(f"Result is : {result}")
# #                     # data = json.loads(result)
# #                     data = parse_mistral_response(result)

# #                     smells = data.get("smells", [])
# #                     new_code = data.get("refactored_code", code)

# #                     if smells:
# #                         smell_report[path] = smells

# #                     if new_code != code:
# #                         with open(path, "w", encoding="utf-8") as f:
# #                             f.write(new_code)
# #                         updated_files.append(path)

# #                 except Exception as e:
# #                     print("Error parsing:", e)
# #                 count+=1
# #                 if count>=MAXFILES:
# #                     return updated_files, smell_report

# #     return updated_files, smell_report



# # # ---------------- COMMIT + PUSH ----------------
# # def commit_and_push(repo, updated_files):
# #     if not updated_files:
# #         print("No changes made.")
# #         return False

# #     repo.git.add(A=True)
# #     repo.index.commit("Automated refactoring using Mistral")

# #     origin = repo.remote(name="origin")
# #     origin.push(NEW_BRANCH)

# #     print("Changes pushed.")
# #     return True


# # # ---------------- CREATE PR ----------------
# # def create_pr(updated_files, smell_report):
# #     g = Github(GITHUB_TOKEN)
# #     repo = g.get_repo(REPO_NAME)

# #     body = "### Automated Design Smell Detection & Refactoring (Mistral)\n\n"

# #     if smell_report:
# #         body += "#### Detected Smells\n"
# #         for file, smells in smell_report.items():
# #             body += f"\n**{file}**\n"
# #             for s in smells:
# #                 body += f"- {s}\n"

# #     body += "\n#### Refactored Files\n"
# #     for f in updated_files:
# #         body += f"- {f}\n"

# #     repo.create_pull(
# #         title="Mistral Design Smell Refactoring",
# #         body=body,
# #         head=NEW_BRANCH,
# #         base=BASE_BRANCH
# #     )



# # # ---------------- MAIN PIPELINE ----------------
# # def run_pipeline():
# #     repo = clone_repo()
# #     updated_files,smell_report = process_files()

# #     if commit_and_push(repo, updated_files):
# #         create_pr(updated_files,smell_report)


# # if __name__ == "__main__":
# #     run_pipeline()
# # import os
# # from git import Repo
# # from github import Github
# # from mistralai import Mistral
# # from dotenv import load_dotenv

# # load_dotenv()

# # # ---------------- CONFIG ----------------
# # GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
# # MISTRAL_API_KEY = os.getenv("MISTRAL_API_KEY")
# # REPO_URL = os.getenv("REPO_URL")
# # REPO_NAME = os.getenv("REPO_NAME")
# # BASE_BRANCH = os.getenv("BASE_BRANCH")

# # LOCAL_PATH = "repo"
# # NEW_BRANCH = "mistral-refactor"
# # from datetime import datetime

# # def generate_branch_name():
# #     timestamp = datetime.now().strftime("%Y%m%d-%H%M%S")
# #     return f"mistral-refactor-{timestamp}"

# # # ---------------- MISTRAL ----------------
# # mistral = Mistral(api_key=MISTRAL_API_KEY)

# # def analyze_code(code):
# #     prompt = f"""
# # You are a software design expert.

# # Tasks:
# # 1. Identify design smells.
# # 2. Refactor the code.
# # 3. Preserve functionality.

# # STRICT RULES:
# # - Return ONLY valid JSON.
# # - Do NOT use markdown.
# # - Do NOT add explanations.
# # - refactored_code must contain only plain code.

# # Format:

# # {{
# # "smells": ["smell1", "smell2"],
# # "refactored_code": "code here"
# # }}

# # Code:
# # {code}
# # """


# #     response = mistral.chat.complete(
# #         model="mistral-small",
# #         messages=[{"role": "user", "content": prompt}],
# #         temperature=0
# #     )

# #     return response.choices[0].message.content



# # # ---------------- CLONE + BRANCH ----------------
# # def clone_repo():
# #     if not os.path.exists(LOCAL_PATH):
# #         print("Cloning repository...")
# #         Repo.clone_from(REPO_URL, LOCAL_PATH)

# #     repo = Repo(LOCAL_PATH)
# #     git = repo.git

# #     # Create new branch
# #     try:
# #         repo.git.checkout("-b", NEW_BRANCH)
# #     except:
# #         repo.git.checkout(NEW_BRANCH)

# #     return repo

# # import json
# # import re

# # def parse_mistral_response(text):
# #     # Remove markdown ```json ``` wrappers
# #     text = text.strip()

# #     # Extract JSON block if wrapped
# #     match = re.search(r"\{.*\}", text, re.DOTALL)
# #     if not match:
# #         raise ValueError("No JSON found")

# #     json_text = match.group(0)

# #     data = json.loads(json_text)

# #     # Clean refactored code (remove ```java ``` if present)
# #     code = data.get("refactored_code", "")

# #     code = re.sub(r"```java", "", code)
# #     code = re.sub(r"```", "", code)
# #     code = code.strip()

# #     data["refactored_code"] = code

# #     return data

# # # ---------------- PROCESS FILES ----------------
# # import json

# # def process_files():
# #     updated_files = []
# #     smell_report = {}
# #     MAXFILES = 3
# #     count = 0
# #     for root, dirs, files in os.walk(LOCAL_PATH):
# #         for file in files:
# #             if file.endswith(".java"):
# #                 path = os.path.join(root, file)

# #                 with open(path, "r", encoding="utf-8") as f:
# #                     code = f.read()

# #                 if len(code) < 50:
# #                     continue

# #                 print("Analyzing:", path)

# #                 try:
# #                     result = analyze_code(code)
# #                     print(f"Result is : {result}")
# #                     # data = json.loads(result)
# #                     data = parse_mistral_response(result)

# #                     smells = data.get("smells", [])
# #                     new_code = data.get("refactored_code", code)

# #                     if smells:
# #                         smell_report[path] = smells

# #                     if new_code != code:
# #                         with open(path, "w", encoding="utf-8") as f:
# #                             f.write(new_code)
# #                         updated_files.append(path)

# #                 except Exception as e:
# #                     print("Error parsing:", e)
# #                 count+=1
# #                 if count>=MAXFILES:
# #                     return updated_files, smell_report

# #     return updated_files, smell_report



# # # ---------------- COMMIT + PUSH ----------------
# # def commit_and_push(repo, updated_files):
# #     if not updated_files:
# #         print("No changes made.")
# #         return False

# #     repo.git.add(A=True)
# #     repo.index.commit("Automated refactoring using Mistral")

# #     origin = repo.remote(name="origin")
# #     origin.push(NEW_BRANCH)

# #     print("Changes pushed.")
# #     return True


# # # ---------------- CREATE PR ----------------

# # def create_pr(branch_name, updated_files, smell_report):
# #     from github import Auth

# #     auth = Auth.Token(GITHUB_TOKEN)
# #     g = Github(auth=auth)

# #     repo = g.get_repo(REPO_NAME)

# #     body = f"""
# # ### Detected Design Smells
# # {smell_report}

# # ### Refactored Files
# # {updated_files}
# # """

# #     repo.create_pull(
# #         title=f"Mistral Refactoring - {branch_name}",
# #         body=body,
# #         head=branch_name,
# #         base=BASE_BRANCH
# #     )

# # def create_branch(repo, branch_name):
# #     new_branch = repo.create_head(branch_name)
# #     new_branch.checkout()



# # # ---------------- MAIN PIPELINE ----------------
# # def run_pipeline():
# #     branch_name = generate_branch_name()
# #     print("Branch:", branch_name)

# #     repo = clone_repo()
# #     create_branch(repo, branch_name)

# #     updated_files, smell_report = process_files()

# #     if updated_files:
# #         commit_and_push(repo, branch_name)
# #         create_pr(branch_name, updated_files, smell_report)



# # if __name__ == "__main__":
# #     run_pipeline()
# import os
# import json
# import re
# from datetime import datetime
# from git import Repo
# from github import Github, Auth
# from mistralai import Mistral
# from dotenv import load_dotenv

# load_dotenv()

# # ---------------- CONFIG ----------------
# GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
# MISTRAL_API_KEY = os.getenv("MISTRAL_API_KEY")
# REPO_URL = os.getenv("REPO_URL")              # https://github.com/owner/repo.git
# REPO_NAME = os.getenv("REPO_NAME")            # owner/repo
# BASE_BRANCH = os.getenv("BASE_BRANCH")

# LOCAL_PATH = "repo"

# # ---------------- BRANCH NAME ----------------
# def generate_branch_name():
#     timestamp = datetime.now().strftime("%Y%m%d-%H%M%S")
#     return f"mistral-refactor-{timestamp}"

# # ---------------- MISTRAL ----------------
# mistral = Mistral(api_key=MISTRAL_API_KEY)

# def analyze_code(code):
#     prompt = f"""
# You are a software design expert.

# Tasks:
# 1. Identify design smells.
# 2. Refactor the code.
# 3. Preserve functionality.

# STRICT RULES:
# - Return ONLY valid JSON.
# - No markdown.
# - No explanations.

# Format:
# {{
# "smells": [],
# "refactored_code": ""
# }}

# Code:
# {code}
# """

#     response = mistral.chat.complete(
#         model="mistral-small",
#         messages=[{"role": "user", "content": prompt}],
#         temperature=0
#     )

#     return response.choices[0].message.content


# # ---------------- JSON PARSER ----------------
# def parse_mistral_response(text):
#     text = text.strip()
#     match = re.search(r"\{.*\}", text, re.DOTALL)
#     if not match:
#         raise ValueError("No JSON found")

#     data = json.loads(match.group(0))

#     code = data.get("refactored_code", "")
#     code = re.sub(r"```.*?\n", "", code)
#     code = code.replace("```", "").strip()

#     data["refactored_code"] = code
#     return data


# # ---------------- CLONE REPO ----------------
# # def clone_repo():
# #     if not os.path.exists(LOCAL_PATH):
# #         print("Cloning repository...")

# #         # Authenticated clone (for private repo)
# #         auth_url = REPO_URL.replace(
# #             "https://",
# #             f"https://{GITHUB_TOKEN}@"
# #         )

# #         Repo.clone_from(auth_url, LOCAL_PATH)

# #     return Repo(LOCAL_PATH)

# def clone_repo():
#     print("Using repository checked out by GitHub Actions...")
#     return Repo(".")


# # ---------------- CREATE BRANCH ----------------
# # def create_branch(repo, branch_name):
# #     git = repo.git
# #     git.checkout(BASE_BRANCH)
# #     git.pull()

# #     print("Creating branch:", branch_name)
# #     repo.git.checkout("-b", branch_name)
# def create_branch(repo, branch_name):
#     git = repo.git

#     # Fetch base branch from origin to ensure it exists
#     git.fetch('origin', BASE_BRANCH)

#     # Checkout base branch from origin
#     git.checkout('-b', branch_name, f'origin/{BASE_BRANCH}')
#     print("Created new branch:", branch_name)



# # ---------------- PROCESS FILES ----------------
# # def process_files():
# #     updated_files = []
# #     smell_report = {}
# #     MAXFILES = 3
# #     count = 0

# #     for root, _, files in os.walk(LOCAL_PATH):
# #         for file in files:
# #             if file.endswith(".java"):
# #                 path = os.path.join(root, file)

# #                 with open(path, "r", encoding="utf-8") as f:
# #                     code = f.read()

# #                 if len(code) < 50:
# #                     continue

# #                 print("Analyzing:", path)

# #                 try:
# #                     result = analyze_code(code)
# #                     data = parse_mistral_response(result)

# #                     smells = data.get("smells", [])
# #                     new_code = data.get("refactored_code", code)

# #                     if smells:
# #                         smell_report[path] = smells

# #                     if new_code and new_code != code:
# #                         with open(path, "w", encoding="utf-8") as f:
# #                             f.write(new_code)
# #                         updated_files.append(path)

# #                 except Exception as e:
# #                     print("Error:", e)

# #                 count += 1
# #                 if count >= MAXFILES:
# #                     return updated_files, smell_report

# #     return updated_files, smell_report
# def process_files():
#     updated_files = []
#     smell_report = {}
#     changes_report = {}

#     MAXFILES = 3
#     count = 0

#     for root, dirs, files in os.walk(LOCAL_PATH):
#         for file in files:
#             if file.endswith(".java"):
#                 path = os.path.join(root, file)

#                 with open(path, "r", encoding="utf-8") as f:
#                     code = f.read()

#                 if len(code) < 50:
#                     continue

#                 print("Analyzing:", path)

#                 try:
#                     result = analyze_code(code)
#                     data = parse_mistral_response(result)

#                     smells = data.get("smells", [])
#                     new_code = data.get("refactored_code", code)

#                     if smells:
#                         smell_report[path] = smells

#                     # If LLM suggested changes
#                     if new_code != code:
#                         with open(path, "w", encoding="utf-8") as f:
#                             f.write(new_code)

#                         updated_files.append(path)
#                         changes_report[path] = new_code  # store LLM changes

#                 except Exception as e:
#                     print("Error parsing:", e)

#                 count += 1
#                 if count >= MAXFILES:
#                     return updated_files, smell_report, changes_report

#     return updated_files, smell_report, changes_report

# # if doesnt work include this
# # ---------------- COMMIT + PUSH ----------------
# # def commit_and_push(repo, branch_name):
# #     if repo.is_dirty(untracked_files=True):
# #         repo.git.add(A=True)
# #         repo.index.commit("Automated refactoring using Mistral")

# #         print("Pushing branch:", branch_name)

# #         origin = repo.remote(name="origin")
# #         origin.push(refspec=f"{branch_name}:{branch_name}")

# #         print("Push successful")
# #         return True
# #     else:
# #         print("No changes to commit")
# #         return False


# # ---------------- CREATE PR ---------------- If doesnt work include this
# # def create_pr(branch_name, updated_files, smell_report):
# #     auth = Auth.Token(GITHUB_TOKEN)
# #     g = Github(auth=auth)

# #     repo = g.get_repo(REPO_NAME)

# #     body = f"""
# # ### Detected Design Smells
# # {json.dumps(smell_report, indent=2)}

# # ### Refactored Files
# # {updated_files}
# # """

# #     print("Creating PR...")

# #     repo.create_pull(
# #         title=f"Mistral Refactoring - {branch_name}",
# #         body=body,
# #         head=branch_name,
# #         base=BASE_BRANCH
# #     )

# #     print("PR created")
# # ---------------- COMMIT + PUSH ----------------
# # def commit_and_push(repo, branch_name):
# #     if repo.is_dirty(untracked_files=True):
# #         repo.git.add(A=True)
# #         repo.index.commit("Automated refactoring using Mistral")

# #         print("Pushing branch:", branch_name)

# #         origin = repo.remote(name="origin")
# #         # Ensure authenticated URL for GitHub Actions
# #         origin_url = REPO_URL.replace("https://", f"https://{GITHUB_TOKEN}@")
# #         origin.set_url(origin_url)
# #         origin.push(refspec=f"{branch_name}:{branch_name}")

# #         print("Push successful")
# #         return True
# #     else:
# #         print("No changes to commit")
# #         return False
# def commit_and_push(repo, branch_name):
#     if repo.is_dirty(untracked_files=True):
#         repo.git.add(A=True)
#         repo.index.commit("Automated refactoring using Mistral")

#         print("Pushing branch:", branch_name)

#         origin = repo.remote(name="origin")
#         # Set remote URL with token for GitHub Actions
#         origin_url = REPO_URL.replace("https://", f"https://{GITHUB_TOKEN}@")
#         origin.set_url(origin_url)

#         # Push branch to remote
#         origin.push(refspec=f"{branch_name}:{branch_name}")

#         print("Push successful")
#         return True
#     else:
#         print("No changes to commit")
#         return False


# # ---------------- CREATE PR ----------------
# # def create_pr(branch_name, updated_files, smell_report):
# #     g = Github(GITHUB_TOKEN)
# #     repo = g.get_repo(REPO_NAME)

# #     body = f"""
# # ### Detected Design Smells
# # {json.dumps(smell_report, indent=2)}

# # ### Refactored Files
# # {updated_files}
# # """

# #     print("Creating PR...")
# #     repo.create_pull(
# #         title=f"Mistral Refactoring - {branch_name}",
# #         body=body,
# #         head=branch_name,
# #         base=BASE_BRANCH
# #     )
# #     print("PR created")
# def create_pr(branch_name, updated_files, smell_report):
#     g = Github(GITHUB_TOKEN)  # PyGithub directly with token
#     repo = g.get_repo(REPO_NAME)

#     body = f"""
# ### Detected Design Smells
# {json.dumps(smell_report, indent=2)}

# ### Refactored Files
# {updated_files}
# """

#     print("Creating PR...")
#     repo.create_pull(
#         title=f"Mistral Refactoring - {branch_name}",
#         body=body,
#         head=branch_name,
#         base=BASE_BRANCH
#     )
#     print("PR created")


# # def save_llm_report(updated_files, smell_report):
# #     """
# #     Saves LLM output into a simple text file.
# #     """

# #     with open("LLM_REPORT.txt", "w", encoding="utf-8") as f:
# #         f.write("LLM Refactoring Report\n")
# #         f.write("======================\n\n")

# #         f.write("Updated Files:\n")
# #         for file in updated_files:
# #             f.write(file + "\n")

# #         f.write("\nDetected Smells:\n")
# #         for file, smells in smell_report.items():
# #             f.write(file + ":\n")
# #             for smell in smells:
# #                 f.write("  - " + smell + "\n")

# #     print("LLM_REPORT.txt created")
# def save_llm_report(updated_files, smell_report, changes_report):
#     with open("LLM_REPORT.txt", "w", encoding="utf-8") as f:
#         f.write("LLM Refactoring Report\n")
#         f.write("======================\n\n")

#         f.write("Updated Files:\n")
#         for file in updated_files:
#             f.write(file + "\n")

#         f.write("\nDetected Smells:\n")
#         for file, smells in smell_report.items():
#             f.write(file + ":\n")
#             for smell in smells:
#                 f.write("  - " + smell + "\n")

#         f.write("\nSuggested Changes (Refactored Code):\n")
#         for file, code in changes_report.items():
#             f.write("\n--- " + file + " ---\n")
#             f.write(code)
#             f.write("\n")

#     print("LLM_REPORT.txt created")

# # ---------------- MAIN PIPELINE ----------------
# def run_pipeline():
#     branch_name = generate_branch_name()
#     print("Branch:", branch_name)

#     repo = clone_repo()
#     create_branch(repo, branch_name)

#     updated_files, smell_report,changes_report = process_files()
#     if updated_files:
#         save_llm_report(updated_files, smell_report,changes_report)
#         pushed = commit_and_push(repo, branch_name)

#         if pushed:
#             create_pr(branch_name, updated_files, smell_report)
#         else:
#             print("No PR created (no changes)")

# if __name__ == "__main__":
#     run_pipeline()
import os
import json
import re
from datetime import datetime
from git import Repo
from github import Github
from mistralai import Mistral
from dotenv import load_dotenv

load_dotenv()

# ---------------- CONFIG ----------------
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")
MISTRAL_API_KEY = os.getenv("MISTRAL_API_KEY")
REPO_URL = os.getenv("REPO_URL")    # e.g., https://github.com/owner/repo.git
REPO_NAME = os.getenv("REPO_NAME")  # e.g., owner/repo
BASE_BRANCH = os.getenv("BASE_BRANCH")

LOCAL_PATH = "."  # Important: Actions checkout is at current directory

# ---------------- BRANCH NAME ----------------
def generate_branch_name():
    timestamp = datetime.now().strftime("%Y%m%d-%H%M%S")
    return f"mistral-refactor-{timestamp}"

# ---------------- MISTRAL ----------------
mistral = Mistral(api_key=MISTRAL_API_KEY)

def analyze_code(code):
    prompt = f"""
You are a software design expert.

Tasks:
1. Identify design smells.
2. Refactor the code.
3. Preserve functionality.

STRICT RULES:
- Return ONLY valid JSON.
- No markdown.
- No explanations.

Format:
{{
"smells": [],
"refactored_code": ""
}}

Code:
{code}
"""

    response = mistral.chat.complete(
        model="mistral-small",
        messages=[{"role": "user", "content": prompt}],
        temperature=0
    )
    return response.choices[0].message.content

# ---------------- JSON PARSER ----------------
def parse_mistral_response(text):
    text = text.strip()
    match = re.search(r"\{.*\}", text, re.DOTALL)
    if not match:
        raise ValueError("No JSON found")
    data = json.loads(match.group(0))
    code = data.get("refactored_code", "")
    code = re.sub(r"```.*?\n", "", code)
    code = code.replace("```", "").strip()
    data["refactored_code"] = code
    return data

# ---------------- CLONE REPO ----------------
def clone_repo():
    print("Using repository checked out by GitHub Actions...")
    return Repo(".")

# ---------------- CREATE BRANCH ----------------
def create_branch(repo, branch_name):
    git = repo.git
    # Fetch base branch from origin
    git.fetch('origin', BASE_BRANCH)
    # Create new branch from base
    git.checkout('-b', branch_name, f'origin/{BASE_BRANCH}')
    print("Created new branch:", branch_name)
    print("Branches available:", [b.name for b in repo.branches])

# ---------------- PROCESS FILES ----------------
def process_files():
    updated_files = []
    smell_report = {}
    changes_report = {}

    MAXFILES = 3
    count = 0

    for root, dirs, files in os.walk(LOCAL_PATH):
        for file in files:
            if file.endswith(".java"):
                path = os.path.join(root, file)
                with open(path, "r", encoding="utf-8") as f:
                    code = f.read()
                if len(code) < 50:
                    continue

                print("Analyzing:", path)

                try:
                    result = analyze_code(code)
                    data = parse_mistral_response(result)
                    smells = data.get("smells", [])
                    new_code = data.get("refactored_code", code)

                    if smells:
                        smell_report[path] = smells

                    if new_code != code:
                        with open(path, "w", encoding="utf-8") as f:
                            f.write(new_code)
                        updated_files.append(path)
                        changes_report[path] = new_code

                except Exception as e:
                    print("Error parsing:", e)

                count += 1
                if count >= MAXFILES:
                    return updated_files, smell_report, changes_report

    return updated_files, smell_report, changes_report

# ---------------- COMMIT + PUSH ----------------
def commit_and_push(repo, branch_name):
    print("Dirty status:", repo.is_dirty(untracked_files=True))
    print("Git status:\n", repo.git.status())

    if repo.is_dirty(untracked_files=True):
        repo.git.add(A=True)
        repo.index.commit("Automated refactoring using Mistral")

        print("Pushing branch:", branch_name)

        origin = repo.remote(name="origin")
        origin_url = REPO_URL.replace("https://", f"https://{GITHUB_TOKEN}@")
        origin.set_url(origin_url)
        origin.push(refspec=f"{branch_name}:{branch_name}")

        print("Push successful")
        return True
    else:
        print("No changes to commit")
        return False

# ---------------- CREATE PR ----------------
def create_pr(branch_name, updated_files, smell_report):
    # g = Github(GITHUB_TOKEN)
    g = Github(auth=Auth.Token(GITHUB_TOKEN))
    repo = g.get_repo(REPO_NAME)

    body = f"""
### Detected Design Smells
{json.dumps(smell_report, indent=2)}

### Refactored Files
{updated_files}
"""
    print("Creating PR...")
    repo.create_pull(
        title=f"Mistral Refactoring - {branch_name}",
        body=body,
        head=branch_name,
        base=BASE_BRANCH
    )
    print("PR created")

# ---------------- SAVE REPORT ----------------
def save_llm_report(updated_files, smell_report, changes_report):
    with open("LLM_REPORT.txt", "w", encoding="utf-8") as f:
        f.write("LLM Refactoring Report\n")
        f.write("======================\n\n")
        f.write("Updated Files:\n")
        for file in updated_files:
            f.write(file + "\n")
        f.write("\nDetected Smells:\n")
        for file, smells in smell_report.items():
            f.write(file + ":\n")
            for smell in smells:
                f.write("  - " + smell + "\n")
        f.write("\nSuggested Changes (Refactored Code):\n")
        for file, code in changes_report.items():
            f.write("\n--- " + file + " ---\n")
            f.write(code)
            f.write("\n")
    print("LLM_REPORT.txt created")

# ---------------- MAIN PIPELINE ----------------
def run_pipeline():
    branch_name = generate_branch_name()
    print("Branch:", branch_name)

    repo = clone_repo()
    create_branch(repo, branch_name)

    updated_files, smell_report, changes_report = process_files()
    if updated_files:
        save_llm_report(updated_files, smell_report, changes_report)
        pushed = commit_and_push(repo, branch_name)
        if pushed:
            create_pr(branch_name, updated_files, smell_report)
        else:
            print("No PR created (no changes)")

if __name__ == "__main__":
    run_pipeline()

